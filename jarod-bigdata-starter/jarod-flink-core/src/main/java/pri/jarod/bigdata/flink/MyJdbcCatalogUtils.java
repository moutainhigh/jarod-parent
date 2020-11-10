package pri.jarod.bigdata.flink;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import org.apache.flink.connector.jdbc.catalog.AbstractJdbcCatalog;
import org.apache.flink.connector.jdbc.catalog.JdbcCatalog;
import org.apache.flink.connector.jdbc.catalog.JdbcCatalogUtils;
import org.apache.flink.connector.jdbc.catalog.PostgresCatalog;
import org.apache.flink.connector.jdbc.dialect.JdbcDialect;
import org.apache.flink.connector.jdbc.dialect.JdbcDialects;
import org.apache.flink.connector.jdbc.dialect.MySQLDialect;
import org.apache.flink.connector.jdbc.dialect.PostgresDialect;
import org.apache.flink.connector.jdbc.table.JdbcDynamicTableFactory;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.api.constraints.UniqueConstraint;
import org.apache.flink.table.catalog.*;
import org.apache.flink.table.catalog.exceptions.*;
import org.apache.flink.table.catalog.stats.CatalogColumnStatistics;
import org.apache.flink.table.catalog.stats.CatalogTableStatistics;
import org.apache.flink.table.expressions.Expression;
import org.apache.flink.table.factories.Factory;
import org.apache.flink.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.flink.util.Preconditions.checkArgument;

/**
 * @author Jarod.Kong
 * @date 2020/9/24 9:37
 */
public class MyJdbcCatalogUtils {
    public static AbstractCatalog createCatalog(String catalogName, String defaultDatabase, String username, String pwd, String baseUrl) {
        AbstractCatalog catalog;
        JdbcDialect dialect = JdbcDialects.get(baseUrl).get();
        if (dialect instanceof PostgresDialect) {
            catalog = new MyPostgresCatalog(catalogName, defaultDatabase, username, pwd, baseUrl);
        } else if (dialect instanceof MySQLDialect) {
            catalog = new MyJdbcCatalog(catalogName, defaultDatabase, username, pwd, baseUrl);
        } else {
            throw new UnsupportedOperationException(
                    String.format("Catalog for '%s' is not supported yet.", dialect)
            );
        }
        return catalog;
    }

    static class MyPostgresCatalog extends PostgresCatalog {

        public MyPostgresCatalog(String catalogName, String defaultDatabase, String username, String pwd, String baseUrl) {
            super(catalogName, defaultDatabase, username, pwd, baseUrl);
        }
    }

    static class MyJdbcCatalog extends AbstractMyJdbcCatalog {
        private static final Logger LOG = LoggerFactory.getLogger(JdbcCatalog.class);

        private final AbstractMyJdbcCatalog internal = null;

        public MyJdbcCatalog(String catalogName, String defaultDatabase, String username, String pwd, String baseUrl) {
            super(catalogName, defaultDatabase, username, pwd, baseUrl);
        }

        // ------ databases -----

        @Override
        public List<String> listDatabases() throws CatalogException {
            try {
                List<Entity> show_databases = Db.use().query("show databases");
                return show_databases.stream().map(entity -> entity.get("database").toString()).collect(Collectors.toList());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return Collections.emptyList();
        }

        @Override
        public CatalogDatabase getDatabase(String databaseName) throws DatabaseNotExistException, CatalogException {
            return listDatabases().stream().filter(d -> databaseName.equalsIgnoreCase(d)).findFirst().map(d -> {
                return new CatalogDatabaseImpl(Map.of("database", d), "");
            }).orElse(null);
        }

        // ------ tables and views ------

        @Override
        public List<String> listTables(String databaseName) throws DatabaseNotExistException, CatalogException {
            try {
                List<Entity> tables = Db.use().query("show tables");
                return tables.stream().map(entity -> entity.get("table").toString()).collect(Collectors.toList());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return Collections.emptyList();
        }

        @Override
        public CatalogBaseTable getTable(ObjectPath tablePath) throws TableNotExistException, CatalogException {
            try {
                List<Entity> tables = Db.use().query("show tables");

                return tables.stream()
                        .filter(entity -> entity.get("table").equals(tablePath))
                        .findFirst()
                        .map(d -> {
                            Map<String, String> collect = d.entrySet().parallelStream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toString()));
                            return new CatalogTableImpl(TableSchema.builder().build(), collect, "");
                        }).orElse(null);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return internal.getTable(tablePath);
        }

        @Override
        public boolean tableExists(ObjectPath tablePath) throws CatalogException {
            try {
                return databaseExists(tablePath.getDatabaseName()) &&
                        listTables(tablePath.getDatabaseName()).contains(tablePath.getObjectName());
            } catch (DatabaseNotExistException e) {
                return false;
            }
        }
    }

    static abstract class AbstractMyJdbcCatalog extends AbstractCatalog {

        private static final Logger LOG = LoggerFactory.getLogger(AbstractJdbcCatalog.class);

        protected final String username;
        protected final String pwd;
        protected final String baseUrl;
        protected final String defaultUrl;

        public AbstractMyJdbcCatalog(String catalogName, String defaultDatabase, String username, String pwd, String baseUrl) {
            super(catalogName, defaultDatabase);

            checkArgument(!StringUtils.isNullOrWhitespaceOnly(username));
            checkArgument(!StringUtils.isNullOrWhitespaceOnly(pwd));
            checkArgument(!StringUtils.isNullOrWhitespaceOnly(baseUrl));

            JdbcCatalogUtils.validateJdbcUrl(baseUrl);

            this.username = username;
            this.pwd = pwd;
            this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
            this.defaultUrl = this.baseUrl + defaultDatabase + "?useSSL=false";
        }

        @Override
        public void open() throws CatalogException {
            // test connection, fail early if we cannot connect to database
            try (Connection conn = DriverManager.getConnection(defaultUrl, username, pwd)) {
            } catch (SQLException e) {
                throw new ValidationException(
                        String.format("Failed connecting to %s via JDBC.", defaultUrl), e);
            }

            LOG.info("Catalog {} established connection to {}", getName(), defaultUrl);
        }

        @Override
        public void close() throws CatalogException {
            LOG.info("Catalog {} closing", getName());
        }

        // ----- getters ------

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return pwd;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        // ------ retrieve PK constraint ------

        protected Optional<UniqueConstraint> getPrimaryKey(DatabaseMetaData metaData, String schema, String table) throws SQLException {

            // According to the Javadoc of java.sql.DatabaseMetaData#getPrimaryKeys,
            // the returned primary key columns are ordered by COLUMN_NAME, not by KEY_SEQ.
            // We need to sort them based on the KEY_SEQ value.
            ResultSet rs = metaData.getPrimaryKeys(null, schema, table);

            Map<Integer, String> keySeqColumnName = new HashMap<>();
            String pkName = null;
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                pkName = rs.getString("PK_NAME"); // all the PK_NAME should be the same
                int keySeq = rs.getInt("KEY_SEQ");
                keySeqColumnName.put(keySeq - 1, columnName); // KEY_SEQ is 1-based index
            }
            List<String> pkFields = Arrays.asList(new String[keySeqColumnName.size()]); // initialize size
            keySeqColumnName.forEach(pkFields::set);
            if (!pkFields.isEmpty()) {
                // PK_NAME maybe null according to the javadoc, generate an unique name in that case
                pkName = pkName == null ? "pk_" + String.join("_", pkFields) : pkName;
                return Optional.of(UniqueConstraint.primaryKey(pkName, pkFields));
            }
            return Optional.empty();
        }

        // ------ table factory ------

        @Override
        public Optional<Factory> getFactory() {
            return Optional.of(new JdbcDynamicTableFactory());
        }

        // ------ databases ------

        @Override
        public boolean databaseExists(String databaseName) throws CatalogException {
            checkArgument(!StringUtils.isNullOrWhitespaceOnly(databaseName));

            return listDatabases().contains(databaseName);
        }

        @Override
        public void createDatabase(String name, CatalogDatabase database, boolean ignoreIfExists) throws DatabaseAlreadyExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void dropDatabase(String name, boolean ignoreIfNotExists, boolean cascade) throws DatabaseNotExistException, DatabaseNotEmptyException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterDatabase(String name, CatalogDatabase newDatabase, boolean ignoreIfNotExists) throws DatabaseNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        // ------ tables and views ------

        @Override
        public void dropTable(ObjectPath tablePath, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void renameTable(ObjectPath tablePath, String newTableName, boolean ignoreIfNotExists) throws TableNotExistException, TableAlreadyExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void createTable(ObjectPath tablePath, CatalogBaseTable table, boolean ignoreIfExists) throws TableAlreadyExistException, DatabaseNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterTable(ObjectPath tablePath, CatalogBaseTable newTable, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> listViews(String databaseName) throws DatabaseNotExistException, CatalogException {
            return Collections.emptyList();
        }

        // ------ partitions ------

        @Override
        public List<CatalogPartitionSpec> listPartitions(ObjectPath tablePath) throws TableNotExistException, TableNotPartitionedException, CatalogException {
            return Collections.emptyList();
        }

        @Override
        public List<CatalogPartitionSpec> listPartitions(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws TableNotExistException, TableNotPartitionedException, CatalogException {
            return Collections.emptyList();
        }

        @Override
        public List<CatalogPartitionSpec> listPartitionsByFilter(ObjectPath tablePath, List<Expression> filters) throws TableNotExistException, TableNotPartitionedException, CatalogException {
            return Collections.emptyList();
        }

        @Override
        public CatalogPartition getPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws PartitionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean partitionExists(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void createPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogPartition partition, boolean ignoreIfExists) throws TableNotExistException, TableNotPartitionedException, PartitionSpecInvalidException, PartitionAlreadyExistsException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void dropPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterPartition(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogPartition newPartition, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        // ------ functions ------

        @Override
        public List<String> listFunctions(String dbName) throws DatabaseNotExistException, CatalogException {
            return Collections.emptyList();
        }

        @Override
        public CatalogFunction getFunction(ObjectPath functionPath) throws FunctionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean functionExists(ObjectPath functionPath) throws CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void createFunction(ObjectPath functionPath, CatalogFunction function, boolean ignoreIfExists) throws FunctionAlreadyExistException, DatabaseNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterFunction(ObjectPath functionPath, CatalogFunction newFunction, boolean ignoreIfNotExists) throws FunctionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void dropFunction(ObjectPath functionPath, boolean ignoreIfNotExists) throws FunctionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        // ------ stats ------

        @Override
        public CatalogTableStatistics getTableStatistics(ObjectPath tablePath) throws TableNotExistException, CatalogException {
            return CatalogTableStatistics.UNKNOWN;
        }

        @Override
        public CatalogColumnStatistics getTableColumnStatistics(ObjectPath tablePath) throws TableNotExistException, CatalogException {
            return CatalogColumnStatistics.UNKNOWN;
        }

        @Override
        public CatalogTableStatistics getPartitionStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws PartitionNotExistException, CatalogException {
            return CatalogTableStatistics.UNKNOWN;
        }

        @Override
        public CatalogColumnStatistics getPartitionColumnStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec) throws PartitionNotExistException, CatalogException {
            return CatalogColumnStatistics.UNKNOWN;
        }

        @Override
        public void alterTableStatistics(ObjectPath tablePath, CatalogTableStatistics tableStatistics, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterTableColumnStatistics(ObjectPath tablePath, CatalogColumnStatistics columnStatistics, boolean ignoreIfNotExists) throws TableNotExistException, CatalogException, TablePartitionedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterPartitionStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogTableStatistics partitionStatistics, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void alterPartitionColumnStatistics(ObjectPath tablePath, CatalogPartitionSpec partitionSpec, CatalogColumnStatistics columnStatistics, boolean ignoreIfNotExists) throws PartitionNotExistException, CatalogException {
            throw new UnsupportedOperationException();
        }
    }

}

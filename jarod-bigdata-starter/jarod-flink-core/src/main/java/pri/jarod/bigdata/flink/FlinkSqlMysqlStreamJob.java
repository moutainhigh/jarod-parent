package pri.jarod.bigdata.flink;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.catalog.AbstractCatalog;

/**
 * @author Jarod.Kong
 * @date 2020/9/24 8:36
 */
public class FlinkSqlMysqlStreamJob {
    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //datasource
        TableEnvironment tableEnv = TableEnvironment.create(EnvironmentSettings.newInstance().build());
        String name = "data_asset";
        String defaultDatabase = "data_asset";
        String username = "root";
        String password = "123456";
        String baseUrl = "jdbc:mysql://192.168.83.110:3306/";

        AbstractCatalog catalog = MyJdbcCatalogUtils.createCatalog(name, defaultDatabase, username, password, baseUrl);
        tableEnv.registerCatalog("data_asset", catalog);

        tableEnv.sqlQuery("select * from data_asset.dap_system_category").printSchema();

        Table query = tableEnv.sqlQuery("select * from dap_system_category").where("a > 1");
        query.printSchema();
        TableResult result = query.execute();
        result.print();

        env.execute();
    }

}

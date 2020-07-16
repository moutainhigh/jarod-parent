package pri.jarod.bigdata.flink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author kongdegang
 * @date 2020/7/15 13:37
 */
public class TableSqlStreamJob {
    public static void main(String[] args) {
//        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
//        StreamTableEnvironment bsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);
//        bsTableEnv.registerTable("X");
//        Table projTable = bsTableEnv.from("X").select("select 1");
//// register the Table projTable as table "projectedTable"
//        tableEnv.createTemporaryView("projectedTable", projTable);
    }
}

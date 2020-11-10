//package pri.jarod.bigdata.flink;
//
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.streaming.api.datastream.DataStreamSink;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
//import org.apache.flink.streaming.connectors.kafka.Kafka010TableSource;
//import org.apache.flink.table.api.EnvironmentSettings;
//import org.apache.flink.table.api.Table;
//import org.apache.flink.table.api.TableEnvironment;
//import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
//
//import static org.apache.flink.table.api.Expressions.$;
//
///**
// * @author Jarod.Kong
// * @date 2020/9/22 16:54
// */
//public class FlinkKafkaStreamJbo {
//    public static void main(String[] args) {
//        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStreamSink<String> test = bsEnv.addSource(new FlinkKafkaConsumer010<String>("test", new RichSinkFunction<String>() {
//        }))
//                .print();
//        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance()
//                .useBlinkPlanner()
//                .inStreamingMode()
//                .build();
////        TableEnvironment tableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);
//// register Orders table
//        bsEnv.setParallelism(1);//本案例并行度别多开，会生成奇怪的文件。
//        DataStreamSource<String> source = bsEnv.readTextFile("D:\\jarodkong\\bluemoonCode\\jarod-parent\\jarod-bigdata-starter\\jarod-flink-core\\src\\main\\resources\\test-data.csv", "UTF-8");
//        SingleOutputStreamOperator<String[]> map = source.map(new MapFunction<String, String[]>() {
//            @Override
//            public String[] map(String value) throws Exception {
//                return value.split(",", -1);
//            }
//        });
//        map.print();
//////        tableEnv.registerCatalog("Orders", );
////
////// scan registered Orders table
////        Table orders = tableEnv.from("Orders");
////// compute revenue for all customers from France
////        Table revenue = orders
////                .filter($("cCountry").isEqual("FRANCE"))
////                .groupBy($("cID"), $("cName"))
////                .select($("cID"), $("cName"), $("revenue").sum().as("revSum"));
//
//        try {
//            bsEnv.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

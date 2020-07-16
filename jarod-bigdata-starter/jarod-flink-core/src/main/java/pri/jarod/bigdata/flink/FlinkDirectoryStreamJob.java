package pri.jarod.bigdata.flink;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;

import java.sql.Timestamp;
import java.util.stream.StreamSupport;

/**
 * 可以读取文件目录
 * 针对比当前时间更新的数据进行监控
 * 但是每次触发是把【全文件数据】重新输出<<不知道是不是状态没控制好导致的，【无法断点续点】
 *
 * @author kongdegang
 * @date 2020/7/15 9:02
 */
public class FlinkDirectoryStreamJob {

    public static void main(String[] args) throws Exception {
        final ParameterTool parameters = ParameterTool.fromArgs(args);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(parameters);
//        env.enableCheckpointing(1000, CheckpointingMode.EXACTLY_ONCE);
//        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        env.enableCheckpointing(1000, CheckpointingMode.EXACTLY_ONCE);
        CheckpointConfig config = env.getCheckpointConfig();
        config.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.getCheckpointConfig().enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        StateBackend stateBackend = new FsStateBackend(new Path("file:///C:\\Users\\Administrator\\logs\\flink-checkpoint"));
        env.setStateBackend(stateBackend);
        env.setParallelism(1);
        String fileDir = "C:\\Users\\Administrator\\logs\\arthas";
        TextInputFormat textInputFormat = new TextInputFormat(new Path(fileDir));
        textInputFormat.setCharsetName("UTF-8");
//        1
//        ContinuousFileMonitoringFunction<String> function = new ContinuousFileMonitoringFunction<>(textInputFormat, FileProcessingMode.PROCESS_CONTINUOUSLY, 1, 1000);
//        DataStreamSource<TimestampedFileInputSplit> dataStreamSource = env.addSource(function);
//        ContinuousFileReaderOperator<String> fileReaderOperator = new ContinuousFileReaderOperator<>(textInputFormat);
//        fileReaderOperator.initializeState(new StateInitializationContextImpl());
//        SingleOutputStreamOperator<String> transform = dataStreamSource.transform("xx", TypeInformation.of(String.class), fileReaderOperator);
//        transform.print();
        IntCounter intCounter = new IntCounter(0);
        env.readFile(textInputFormat, fileDir, FileProcessingMode.PROCESS_CONTINUOUSLY, 1000)
                .map(new RichMapFunction<String, Tuple2<String, String>>() {
                    private LongCounter longCounter;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        longCounter = new LongCounter(0L);
                    }

                    @Override
                    public Tuple2<String, String> map(String value) throws Exception {
                        longCounter.add(1L);
                        return Tuple2.of(String.valueOf(longCounter.getLocalValue()), value);
                    }
                })
                .keyBy("f0")
                .map(new RichMapFunction<Tuple2<String, String>, String>() {
                    private ListState<String> listState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);
                        ListStateDescriptor<String> listStateDesc = new ListStateDescriptor<String>("offset", String.class);
                        listState = getRuntimeContext().getListState(listStateDesc);
                    }

                    @Override
                    public String map(Tuple2<String, String> value) throws Exception {
                        Iterable<String> i = listState.get();
//                        String resultVal = String.join("=>", value.f0, value.f1);
                        String resultVal = value.f1;
                        // 状态去重
                        long b = StreamSupport.stream(i.spliterator(), false).filter(resultVal::equalsIgnoreCase).count();
                        if (b == 0) {
                            listState.add(resultVal);
                            return value.f1;
                        } else {
                            return null;
                        }
                    }

                    @Override
                    public void close() throws Exception {
                        super.close();
//                        listState.clear();
                    }
                })
                .filter(new RichFilterFunction<String>() {
                    @Override
                    public boolean filter(String value) throws Exception {
                        return value != null;
                    }
                })
                .addSink(new RichSinkFunction<String>() {
                    @Override
                    public void invoke(String value, Context context) throws Exception {
                        if (StringUtils.isNotBlank(value))
                            System.out.println(value);
                    }
                });

//        DataStream<String> fileStream = env.re(fileDir, 1000, FileMonitoringFunction.WatchType.REPROCESS_WITH_APPENDED);
//        fileStream.addSink(new RichSinkFunction<String>() {
//            @Override
//            public void invoke(String value, Context context) throws Exception {
//                System.out.println(value);
//            }
//        });
//        DataStreamSource<String> dataSource = env.readTextFile(fileDir, StandardCharsets.UTF_8.name());
//        IntCounter intCounter = new IntCounter(0);
//        dataSource.map(new RichMapFunction<String, Tuple2<Integer, String>>() {
//            @Override
//            public Tuple2<Integer, String> map(String value) throws Exception {
//                intCounter.add(1);
//                return Tuple2.apply(intCounter.getLocalValue(), value);
//            }
//        }).timeWindowAll(Time.milliseconds(500))
//                .apply(new AllWindowFunction<Tuple2<Integer, String>, String, TimeWindow>() {
//                    @Override
//                    public void apply(TimeWindow window, Iterable<Tuple2<Integer, String>> values, Collector<String> out) throws Exception {
//                        for (Tuple2<Integer, String> value : values) {
//                            out.collect(value._2());
//                        }
//                    }
//                })
//                .addSink(new RichSinkFunction<String>() {
//                    @Override
//                    public void invoke(String value, Context context) throws Exception {
//                        System.out.println("读取到文件：" + value);
//                    }
//                });
        env.execute("a");
    }

    @Setter
    @Getter
    @ToString
    public static class FileDto {
        private String line;
        private Timestamp biz;
    }
}

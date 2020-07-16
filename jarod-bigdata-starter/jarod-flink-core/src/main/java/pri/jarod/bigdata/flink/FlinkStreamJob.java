package pri.jarod.bigdata.flink;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author kongdegang
 * @date 2020/7/9 9:40
 */
public class FlinkStreamJob {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        ExecutionEnvironment execEnv = ExecutionEnvironment.getExecutionEnvironment();
//        execEnv.readFile(new FileInputFormat<BaseStreamDto>() {
//            @Override
//            public boolean reachedEnd() throws IOException {
//                return false;
//            }
//
//            @Override
//            public BaseStreamDto nextRecord(BaseStreamDto baseStreamDto) throws IOException {
//                return null;
//            }
//        }, "/xxx/file.doc");
        env.addSource(new RichSourceFunction<BaseStreamDto>() {
            @Override
            public void run(SourceContext<BaseStreamDto> ctx) throws Exception {
                MyStreamDto.FileDto fileDto = MyStreamDto.FileDto.builder().name("xx").content("a112312").build();
                MyStreamDto myStreamDto = MyStreamDto.builder().createTime(Timestamp.valueOf(LocalDateTime.now())).provider("xxx").fileDto(fileDto).build();
                ctx.collect(myStreamDto);
            }

            @Override
            public void cancel() {

            }
        }).map(new RichMapFunction<BaseStreamDto, BaseStreamDto>() {
            @Override
            public MyStreamDto map(BaseStreamDto streamDto) throws Exception {
                Class<? extends BaseStreamDto> streamDtoClass = BaseStreamDto.getBaseStreamDtoClass(MyStreamDto.SCHEME_NAME);
                MyStreamDto myStreamDto = (MyStreamDto) streamDtoClass.newInstance();
                String content = myStreamDto.getFileDto().getContent();
                return null;
            }
        }).rebalance().addSink(new RichSinkFunction<BaseStreamDto>() {
            @Override
            public void invoke(BaseStreamDto value, Context context) throws Exception {

            }
        }).name("");

    }
}

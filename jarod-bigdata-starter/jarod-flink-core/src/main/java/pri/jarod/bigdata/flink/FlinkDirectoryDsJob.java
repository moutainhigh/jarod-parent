package pri.jarod.bigdata.flink;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.LocalEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

/**
 * @author kongdegang
 * @date 2020/7/15 9:02
 */
public class FlinkDirectoryDsJob {

    public static void main(String[] args) throws Exception {
        LocalEnvironment env = ExecutionEnvironment.createLocalEnvironment();
        env.setParallelism(1);
        String fileDir = "C:\\Users\\Administrator\\logs\\arthas";
        DataSource<String> dataSource = env.readTextFile(fileDir, StandardCharsets.UTF_8.name());
        IntCounter intCounter = new IntCounter(0);
        dataSource.output(new RichOutputFormat<String>() {
            @Override
            public void configure(Configuration parameters) {

            }

            @Override
            public void open(int taskNumber, int numTasks) throws IOException {

            }

            @Override
            public void writeRecord(String record) throws IOException {
                intCounter.add(1);
                System.out.println(intCounter.getLocalValue() + "读取到文件：" + record);
            }

            @Override
            public void close() throws IOException {

            }
        });
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

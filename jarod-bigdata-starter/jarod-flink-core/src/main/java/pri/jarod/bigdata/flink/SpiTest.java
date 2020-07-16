package pri.jarod.bigdata.flink;

import org.apache.flink.util.ReflectionUtil;

import java.io.IOException;

/**
 * @author kongdegang
 * @date 2020/7/15 13:14
 */
public class SpiTest {
    public static void main(String[] args) throws IOException {
        Class<? extends BaseStreamDto> streamDtoClass = BaseStreamDto.getBaseStreamDtoClass(MyStreamDto.SCHEME_NAME);
        MyStreamDto myStreamDto = (MyStreamDto) ReflectionUtil.newInstance(streamDtoClass);
        String content = myStreamDto.getScheme();
        System.out.println(content);
    }
}

package pri.jarod.bigdata.flink;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author kongdegang
 * @date 2020/7/9 9:57
 */
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyStreamDto extends BaseStreamDto {
    public static final String SCHEME_NAME = "parserSubject";
    private String subjectType; //试题类型
    private String provider;// 提供者
    private Timestamp createTime;
    private FileDto fileDto;

    @Override
    protected String getScheme() {
        return SCHEME_NAME;
    }

    @Builder
    @ToString
    @Setter
    @Getter
    public static class FileDto implements Serializable {
        private String content;
        private String name;
        private String path;
        private String row;
    }

}

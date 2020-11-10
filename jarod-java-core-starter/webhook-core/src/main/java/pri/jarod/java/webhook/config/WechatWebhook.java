package pri.jarod.java.webhook.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 15:20
 */
@Data
public class WechatWebhook extends IWebhook{
    public WechatWebhook(){
        setType("wechat");
    }
    private String uploadUrl;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,  ToStringStyle.MULTI_LINE_STYLE);
    }

}

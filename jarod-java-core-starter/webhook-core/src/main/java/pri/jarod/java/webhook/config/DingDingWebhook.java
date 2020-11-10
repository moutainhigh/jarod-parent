package pri.jarod.java.webhook.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 15:20
 */
public class DingDingWebhook extends IWebhook{
    public static final String type = "dingidng";
    public DingDingWebhook(){
        setType(type);
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,  ToStringStyle.MULTI_LINE_STYLE);
    }

}

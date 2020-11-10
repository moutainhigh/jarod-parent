package pri.jarod.java.webhook.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 15:17
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = WechatWebhook.class, name = "wechat"),
        @JsonSubTypes.Type(value = DingDingWebhook.class, name = "dingding")
})
@Data
public abstract class IWebhook {
    private String type;
    private String url;

}

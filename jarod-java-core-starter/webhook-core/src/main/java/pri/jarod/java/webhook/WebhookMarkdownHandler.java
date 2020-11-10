package pri.jarod.java.webhook;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * markdown类型的实现，然后返回字符串
 *
 * @author Jarod Kong
 * @date 2020-09-19 14:12
 **/
public class WebhookMarkdownHandler implements WebhookMsgTypeHandler {

    @Override
    public String msgHanlder(Map<String, Object> markdown) {
        String msg = "";
        if (markdown.get("markdown") != null) {
            msg = String.valueOf(markdown.get("markdown"));
        } else {
            msg = JSON.toJSONString(markdown);
        }
        // webhook最大4096长度
        if (msg.length() > 4000) {
            msg = msg.substring(0, 4000)+"...";
        }
        WechatMarkdownWebhookDto webhookDto = new WechatMarkdownWebhookDto(new WechatMarkdownWebhookDto.Markdown(msg));
        return toJson(webhookDto);
    }


}

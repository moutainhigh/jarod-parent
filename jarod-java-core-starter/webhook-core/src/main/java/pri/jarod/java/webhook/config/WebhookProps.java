package pri.jarod.java.webhook.config;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pri.jarod.java.webhook.WebhookClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 15:21
 */
@Data
@Slf4j
public final class WebhookProps {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static WebhookProps webhookProps;

    static {
        ClassPathResource cl = new ClassPathResource("webhook.json");
        try {
            webhookProps = OBJECT_MAPPER.readValue(cl.getFile(), WebhookProps.class);
        } catch (IOException e) {
            log.error("读取文件失败");
            throw new RuntimeException("读取文件失败", e);
        }
    }

    private String version;
    private List<IWebhook> webhooks = Lists.newArrayList();
    private WechatWebhook wechatWebhook;
    private DingDingWebhook dingDingWebhook;

    public static List<WechatWebhook> getWechatWebhook() {
        return webhookProps.getWebhooks().stream().filter(iWebhook -> "wechat".equalsIgnoreCase(iWebhook.getType()))
                .map(d -> (WechatWebhook)d).collect(Collectors.toList());
    }

    public static List<DingDingWebhook> getDingDingWebhook() {
        return webhookProps.getWebhooks().stream().filter(iWebhook -> "dingding".equalsIgnoreCase(iWebhook.getType()))
                .map(d -> (DingDingWebhook)d).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<WechatWebhook> wechatWebhook = getWechatWebhook();
        System.out.println(wechatWebhook);
        String wecharWebhookSendFileUrl = WebhookClient.WECHAR_WEBHOOK_SEND_FILE_URL;
        System.out.println(wecharWebhookSendFileUrl);
    }


}

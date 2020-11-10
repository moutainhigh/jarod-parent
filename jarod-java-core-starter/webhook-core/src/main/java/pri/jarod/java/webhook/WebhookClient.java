package pri.jarod.java.webhook;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import pri.jarod.java.webhook.config.WebhookProps;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static pri.jarod.java.webhook.WechatWebhookDto.NEWS_MSG_TYPE;


/**
 * @author Jarod Kong
 * @date 2020-09-19 14:13
 **/
@Slf4j
public class WebhookClient {
    public static final String WECHAR_WEBHOOK_URL = WebhookProps.getWechatWebhook().get(0).getUrl();

    public static final String WECHAR_WEBHOOK_SEND_FILE_URL =  WebhookProps.getWechatWebhook().get(0).getUploadUrl();

    public static String getWechatWebhookGitlabReqBody(Map<String, Object> gitlabRequestBody, String webhookMsgType) {

        Objects.requireNonNull(gitlabRequestBody, "gitlab请求数据不可为空");
        if (webhookMsgType == null) {
            webhookMsgType = NEWS_MSG_TYPE;
        }
        Webhook webhook;
        if (webhookMsgType.equalsIgnoreCase(NEWS_MSG_TYPE)) {
            webhook = new NewWebhook();
        } else {
            webhook = new MarkdownWebhook();
        }
        return webhook.getMsgByMap(gitlabRequestBody);
    }

    /**
     * 发送消费
     *
     * @param markdownMsg
     */
    public static void sendExceptionMsg(String markdownMsg) {
        String reqBody = WebhookClient.getWechatWebhookMarkdown(markdownMsg);
        HttpRequest post = HttpRequest.post(WECHAR_WEBHOOK_URL);
        post.body(reqBody, "application/json");
        try (HttpResponse response = post.execute()) {
            String body = response.body();
            log.info("resp:{}, status:{}", body, response.getStatus());
        }
    }

    /**
     * 构建webhook的markdown json
     *
     * @param markdownMsg
     * @return
     */
    public static String getWechatWebhookMarkdown(String markdownMsg) {
        MarkdownWebhook markdownWebhook = new MarkdownWebhook();
        markdownWebhook.setMsgTypeHandler(new WebhookMarkdownHandler());
        return markdownWebhook.getMsgByMap(ImmutableMap.of("markdown", markdownMsg));
    }

    /**
     * 构建webhook的markdown json
     *
     * @param content 内容
     * @return
     */
    public static void sendWechatWebhook(String content) {
        if (StrUtil.isNotBlank(content)) {
            // 数据少，用markdown
            if (content.getBytes(StandardCharsets.UTF_8).length <= 5) {
                sendExceptionMsg(content);
            } else {
                sendWechatWebhookFile(content, null, null);
            }
        }
    }

    /**
     * 构建webhook的markdown json
     *
     * @param content 内容要大于5byte
     * @return
     */
    public static void sendWechatWebhookFile(String content, String fileNamePrefix, String fileNameSuffix) {
        MarkdownWebhook markdownWebhook = new MarkdownWebhook();
        markdownWebhook.setMsgTypeHandler(new WebhookFileGitlabHandler());
        Map<String, Object> context = new HashMap<>();
        context.put("fileContent", content);
        context.put("fileNameSuffix", fileNameSuffix);
        context.put("fileNamePrefix", fileNamePrefix);
        String msgByMap = markdownWebhook.getMsgByMap(context);
        HttpRequest post = HttpRequest.post(WECHAR_WEBHOOK_URL);
        post.body(msgByMap, "application/json");
        try (HttpResponse response = post.execute()) {
            String body = response.body();
            log.info("resp:{}, status:{}", body, response.getStatus());
        }
    }

   /* public static void main(String[] args) {
        sendWechatWebhookFile("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttes" +
                "ttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest", "", "");
    }*/
}

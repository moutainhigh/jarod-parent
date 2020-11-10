package pri.jarod.java.webhook;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:14
 **/
public class MarkdownWebhook extends Webhook {
    public MarkdownWebhook() {
        setMsgTypeHandler(new WebhookMarkdownGitlabHandler());
    }
}

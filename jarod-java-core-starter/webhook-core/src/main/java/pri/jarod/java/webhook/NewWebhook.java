package pri.jarod.java.webhook;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:15
 **/
public class NewWebhook extends Webhook {
    public NewWebhook() {
        setMsgTypeHandler(new WebhookNewsGitlabHandler());
    }

}

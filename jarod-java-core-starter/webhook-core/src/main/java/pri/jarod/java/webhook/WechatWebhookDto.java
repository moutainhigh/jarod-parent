package pri.jarod.java.webhook;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:29
 **/
public abstract class WechatWebhookDto {
    public static final String MARKDOWN_MSG_TYPE = "markdown";

    public static final String NEWS_MSG_TYPE = "news";

    protected String msgtype;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}

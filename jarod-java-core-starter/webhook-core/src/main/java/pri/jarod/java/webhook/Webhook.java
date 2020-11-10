package pri.jarod.java.webhook;

import java.util.Map;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:05
 **/
public abstract class Webhook {
    protected WebhookMsgTypeHandler msgTypeHandler;

    protected void setMsgTypeHandler(WebhookMsgTypeHandler msgTypeHandler) {
        this.msgTypeHandler = msgTypeHandler;
    }

    /**
     * 返回结果
     *
     * @param context
     * @return
     */
    public String getMsgByMap(Map<String, Object> context) {
        if (msgTypeHandler != null) {
            return msgTypeHandler.msgHanlder(context);
        }
        return null;
    }
}

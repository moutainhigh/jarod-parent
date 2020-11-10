package pri.jarod.java.webhook;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:06
 **/
@FunctionalInterface
public interface WebhookMsgTypeHandler {
    /**
     * 利用map数据转为对应类型的字符串
     *
     * @param baseMap map
     * @return 最终结果jsonstr {@link JSON#toJSONString()}
     */
    String msgHanlder(Map<String, Object> baseMap);

    /**
     * 抽取转为json str
     *
     * @param webhookDto x
     * @return x
     */
    default String toJson(WechatWebhookDto webhookDto) {
        return JSON.toJSONString(webhookDto);
    }
}

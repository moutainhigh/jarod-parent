package pri.jarod.java.webhook;

import java.util.List;
import java.util.Map;

/**
 * markdown类型的实现，然后返回字符串
 *
 * @author Jarod Kong
 * @date 2020-09-19 14:12
 **/
public class WebhookMarkdownGitlabHandler extends WebhookMarkdownHandler {

    @Override
    public String msgHanlder(Map<String, Object> map) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Map<String, Object>> commits = (List) map.get("commits");
        StringBuilder sb = new StringBuilder();
        sb.append("项目构建启动<font color=\\\"warning\\\"></font>，构建内容如下：\\n");
        for (Map<String, Object> m : commits) {
            Map author = (Map) m.get("author");
            String row = ">提交者:<font color=\\\"comment\\\">" + author.get("name") + "</font>\n" +
                    ">提交内容:<font color=\\\"comment\\\">" + m.get("message") + "</font>\n" +
                    ">链接:<font color=\\\"comment\\\">" + m.get("url") + "</font>\n" +
                    ">提交时间:<font color=\\\"comment\\\">" + m.get("timestamp") + "</font>\n";
            sb.append(row).append("\\n");
        }
        WechatMarkdownWebhookDto webhookDto = new WechatMarkdownWebhookDto(new WechatMarkdownWebhookDto.Markdown(sb.toString()));
        return toJson(webhookDto);
    }


}

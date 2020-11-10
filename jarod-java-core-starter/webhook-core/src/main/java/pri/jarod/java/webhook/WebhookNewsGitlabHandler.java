package pri.jarod.java.webhook;

import java.util.*;

/**
 * http://gitlab.admin.bluemoon.com.cn/help/user/project/integrations/webhooks
 *
 * @author Jarod Kong
 * @date 2020-09-19 14:11
 **/
public class WebhookNewsGitlabHandler implements WebhookMsgTypeHandler {
    /*public static void main(String[] args) {
        System.out.println();
    }*/

    @Override
    public String msgHanlder(Map<String, Object> map) {
        Object userName = map.get("user_name");
//        Object userEmail = map.get("user_email");
        Object userAvatar = map.get("user_avatar");
        // 增加分支信息
        Object ref = map.get("ref");
        String[] strs = Objects.toString(ref, "").split("/", -1);
        String brachName = "";
        //noinspection AliControlFlowStatementWithoutBraces
        if (strs.length >= 3) {
            brachName = "(" + strs[2] + ")";
        }
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Map<String, Object>> commits = (List) map.get("commits");
        Map<String, Object> commit = commits.get(0);
        String url = Objects.toString(commit.get("url"));
        @SuppressWarnings({"unchecked", "rawtypes"})
        Map<String, Object> author = (Map) commit.getOrDefault("author", new HashMap<String, Object>(1));
        Object newestCommitter = author.getOrDefault("name", userName);
        String message = Objects.toString(commit.get("message"));
        String appName = "数据资产项目";
        WechatNewsWebhookDto reqBody = new WechatNewsWebhookDto(
                new WechatNewsWebhookDto.News(
                        Collections.singletonList(new WechatNewsWebhookDto.News.Article(
                                String.format("%s构建中", appName + brachName),
                                String.format("Pusher：%s ,\n" +
                                        "最新Committer：%s ,\n" +
                                        "info:%s", userName, newestCommitter, message),
                                Objects.toString(url),
                                Objects.toString(userAvatar)
                        )))
        );
        return toJson(reqBody);
    }
}

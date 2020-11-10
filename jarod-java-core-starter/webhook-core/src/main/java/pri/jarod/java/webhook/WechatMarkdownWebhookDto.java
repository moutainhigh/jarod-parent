package pri.jarod.java.webhook;

/**
 * 存储webhook结构数据
 *
 * @author Jarod Kong
 * @date 2020-09-19 14:30
 **/
public class WechatMarkdownWebhookDto extends WechatWebhookDto {

    private final Markdown markdown;

    public WechatMarkdownWebhookDto(Markdown markdown) {
        this.markdown = markdown;
        setMsgtype(MARKDOWN_MSG_TYPE);
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    static class Markdown {
        private final String content;

        public Markdown(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}

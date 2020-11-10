package pri.jarod.java.webhook;

import java.util.List;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:29
 **/
public class WechatNewsWebhookDto extends WechatWebhookDto {
    private final News news;

    public WechatNewsWebhookDto(News news) {
        this.news = news;
        setMsgtype(NEWS_MSG_TYPE);
    }

    public News getNews() {
        return news;
    }

    @Override
    public String getMsgtype() {
        return msgtype;
    }

    @Override
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public static class News {
        private final List<Article> articles;

        public News(List<Article> articles) {
            this.articles = articles;
        }

        public List<Article> getArticles() {
            return articles;
        }

        public static class Article {
            private final String title;
            private final String description;
            private final String url;
            private final String picurl;

            public Article(String title, String description, String url, String picurl) {
                this.title = title;
                this.description = description;
                this.url = url;
                this.picurl = picurl;
            }

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }

            public String getUrl() {
                return url;
            }

            public String getPicurl() {
                return picurl;
            }
        }
    }
}

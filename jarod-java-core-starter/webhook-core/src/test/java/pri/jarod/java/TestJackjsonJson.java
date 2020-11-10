package pri.jarod.java;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pri.jarod.java.webhook.config.WebhookProps;

import java.io.IOException;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 15:25
 */
public class TestJackjsonJson {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource cl = new ClassPathResource("webhook.json");
        WebhookProps webhookProps = objectMapper.readValue(cl.getFile(), WebhookProps.class);
        System.out.println(ToStringBuilder.reflectionToString(webhookProps));
    }
}

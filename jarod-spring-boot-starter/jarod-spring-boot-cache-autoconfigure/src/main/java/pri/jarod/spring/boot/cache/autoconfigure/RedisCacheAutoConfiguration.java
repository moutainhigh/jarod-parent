package pri.jarod.spring.boot.cache.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pri.jarod.spring.boot.cache.core.JedisTemplete;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author kongdegang
 * @date 2020/7/15 15:18
 */
@Configuration
@ConditionalOnClass(RedisCacheAutoConfiguration.MyRedisProperties.class)
@EnableConfigurationProperties(RedisCacheAutoConfiguration.MyRedisProperties.class)
public class RedisCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "jedisTemplete")
    public JedisTemplete jedisTemplete(MyRedisProperties myRedisProperties) {
        JedisTemplete jedisTemplete = new JedisTemplete();

        jedisTemplete.setJedisPool(myRedisProperties.getJedisPool());

        return jedisTemplete;
    }

    @ConfigurationProperties(value = "jarod-config.redis")
    public static class MyRedisProperties {

        //Redis服务器IP
        @Setter
        @Getter
        private String host;
        @Setter
        @Getter
        //Redis的端口号
        private int port;
        @Setter
        @Getter
        //访问密码
        private String password;
        @Setter
        @Getter
        private String username;


        /**
         * 初始化Redis连接池
         */
        public JedisPool getJedisPool() {
            JedisPoolConfig config = new JedisPoolConfig();
            //省略具体设置
            return new JedisPool(config, host, port);
        }
    }
}

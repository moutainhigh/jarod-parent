package pri.jarod.spring.boot.cache.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author kongdegang
 * @date 2020/7/15 15:35
 */
public class JedisTemplete {
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public String getValue(String key) {
        return getJedis().get(key);
    }
}


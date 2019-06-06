package com.zhiyun.readwrite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @Title: RedisConfig
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/6/617:04
 */
@Component
@Configuration
public class RedisConfig {

    /**
     * redis 防止key value 前缀乱码.
     *
     * @param factory redis连接 factory
     * @return redisTemplate
     */
   @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
    @ConfigurationProperties(prefix = "spring.redis")
    public static RedisTemplate<String, Object> initRedis(Integer indexDb, RedisTemplate<String, Object> redisTemplate) {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setDatabase(indexDb);
        redisConnectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

   /* public RedisTemplate<String, String> getStringStringRedisTemplate(RedisConnectionFactory factory2) {
        StringRedisTemplate template = new StringRedisTemplate(factory2);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.string());
        return template;
    }*/
}

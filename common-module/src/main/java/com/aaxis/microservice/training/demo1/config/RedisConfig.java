package com.aaxis.microservice.training.demo1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by terrence on 2018/10/13.
 */
@Slf4j
public class RedisConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }



    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        log.info("JedisConnectionFactory bean init success.");
        return factory;
    }



    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate() {
        JedisConnectionFactory jedisConnectionFactory = getConnectionFactory();
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        //        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //        ObjectMapper om = new ObjectMapper();
        //        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //        jackson2JsonRedisSerializer.setObjectMapper(om);
        //        template.setValueSerializer(jackson2JsonRedisSerializer);
        //        template.afterPropertiesSet();
        return template;
    }
}
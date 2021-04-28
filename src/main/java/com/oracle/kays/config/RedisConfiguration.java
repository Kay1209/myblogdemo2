//package com.oracle.kays.config;
//
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.*;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import javax.annotation.Resource;
//
///**
// * Redis 配置类
// *
// * @author Leon
// * @version 2018/6/17 17:46
// */
//@Configuration
//@EnableCaching
//@Slf4j
//public class RedisConfiguration extends CachingConfigurerSupport {
//
//    @Autowired
//    private JedisConnectionFactory jedisConnectionFactory;
//
//
//    /**
//     * RedisTemplate配置
//     * key 和 value 都为String类型
//     * 都使用Jackson2JsonRedisSerializer进行序列化
//     */
//    @Bean(name = "redisStringTemplate")
//    public RedisTemplate<String, String> redisTemplateForString(RedisConnectionFactory jedisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    /**
//     * RedisTemplate配置
//     * key 为String类型
//     * value 为Object
//     * 都使用Jackson2JsonRedisSerializer进行序列化
//     */
//    @Bean(name = "redisTemplate")
//    public RedisTemplate<String, Object> redisTemplateForObject(RedisConnectionFactory jedisConnectionFactory ) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        // 使用Jackson2JsonRedisSerializer来序列化/反序列化redis的value值
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
//                Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL,
//                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        // value
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        // 使用StringRedisSerializer来序列化/反序列化redis的key值
//        RedisSerializer<?> redisSerializer = new StringRedisSerializer();
//        // key
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setHashKeySerializer(redisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}

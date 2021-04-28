//package com.oracle.kays.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//@Configuration
//public class DataJedisProperties {
//    @Value("${spring.redis.host}")
//    private  String host;
//    @Value("${spring.redis.password}")
//    private  String password;
//    @Value("${spring.redis.port}")
//    private  int port;
//    @Value("${spring.redis.database}")
//    private int database;
//    @Value("${spring.redis.timeout}")
//    private  int timeout;
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private int maxIdle;
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private long maxWaitMillis;
//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        lg.info("Create JedisConnectionFactory successful");
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
//        factory.setTimeout(timeout);
//        factory.setPassword(password);
//        return factory;
//    }
//
//    @Bean
//    public JedisPool redisPoolFactory() {
//        lg.info("JedisPool init successful，host -> [{}]；port -> [{}]", host, port);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
//        return jedisPool;
//    }
//
//}

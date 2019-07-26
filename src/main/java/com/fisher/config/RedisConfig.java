package com.fisher.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig getJedisPoolConfig(@Value("${spring.jedis.pool.config.maxIdle}") int maxIdle,
                           @Value("${spring.jedis.pool.config.maxWaitMillis}") int maxWaitMillis,
                           @Value("${spring.jedis.pool.config.testOnBorrow}") boolean testOnBorrow ){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return jedisPoolConfig;
    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory getJedisConnectionFactory(@Value("${spring.jedis.pool.host}") String host,
                                                            @Value("${spring.jedis.pool.port}") int port,
                                                            @Value("${spring.jedis.pool.password}") String password,
                                                            @Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate getRedisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

}

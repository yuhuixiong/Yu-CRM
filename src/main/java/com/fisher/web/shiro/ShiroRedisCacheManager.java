package com.fisher.web.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;


public class ShiroRedisCacheManager implements CacheManager {


    private ShiroRedisCache shiroRedisCache;

    public ShiroRedisCacheManager(ShiroRedisCache shiroRedisCache) {
        this.shiroRedisCache = shiroRedisCache;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return shiroRedisCache;
    }

//    private RedisTemplate<byte[],byte[]> redisTemplate;
//
//    public ShiroRedisCacheManager(RedisTemplate redisTemplate){
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    protected Cache createCache(String s) throws CacheException {
//        System.out.println("createCache");
//        return new ShiroRedisCache(redisTemplate,s);
//    }
}

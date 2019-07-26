package com.fisher.web.shiro;

import com.alibaba.fastjson.JSON;
import com.fisher.utils.ObjectUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate redisTemplate;
    private String prefix = "shiro_redis";


    public String getPrefix() {
        return prefix+":";
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ShiroRedisCache(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public ShiroRedisCache(RedisTemplate redisTemplate,String prefix){
        this(redisTemplate);
        this.prefix = prefix;
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("get");
        if (k == null) {
            return null;
        }
        byte[] bytes = getBytesKey(k);
        byte[] value = (byte[]) redisTemplate.opsForValue().get(bytes);
        if(value != null){
            return (V) ObjectUtil.deserialize(value);
        }
        return null;

    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("put: " + prefix);
        if (k== null || v == null) {
            return null;
        }
        System.out.println("key: "+ JSON.toJSONString(k));
        System.out.println("value: "+ v.getClass().getName());
        byte[] bytes = getBytesKey(k);
        byte[] value = ObjectUtil.serialize(v);
        redisTemplate.opsForValue().set(bytes, value);
        System.out.println("ok");
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        System.out.println("remove");
        if(k==null){
            return null;
        }
        byte[] bytes =getBytesKey(k);
        byte[] value = (byte[]) redisTemplate.opsForValue().get(bytes);
        redisTemplate.delete(bytes);
        if(value != null){
            return (V) ObjectUtil.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.getConnectionFactory().getConnection().flushDb();

    }

    @Override
    public int size() {
        return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
    }

    @Override
    public Set<K> keys() {
        byte[] bytes = (getPrefix()+"*").getBytes();
        Set<byte[]> keys = redisTemplate.keys(bytes);
        Set<K> sets = new HashSet<K>();
        for (byte[] key:keys) {
            sets.add((K)key);
        }
        return sets;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<V>(keys.size());
        for(K k :keys){
            values.add(get(k));
        }
        return values;
    }

    private byte[] getBytesKey(K key){
        if(key instanceof String){
            String prekey = this.getPrefix() + key;
            return prekey.getBytes();
        }else {
            return ObjectUtil.serialize(key);
        }
    }

}

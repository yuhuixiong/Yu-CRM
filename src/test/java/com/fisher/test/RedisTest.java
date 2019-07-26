package com.fisher.test;

import com.alibaba.fastjson.JSON;
import com.fisher.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void put(){
        User user = new User();
        user.setUsername("哈哈");
        user.setPassword("5156165156");
        redisTemplate.opsForValue().set("admin", "admin");
    }

    @Test
    public void get(){
        byte[] bytes = (byte[]) redisTemplate.boundHashOps("test").get("66".getBytes());
        String str = new String(bytes);
        System.out.println(str);
    }

}

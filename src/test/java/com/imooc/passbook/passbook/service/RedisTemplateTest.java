package com.imooc.passbook.passbook.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * redis 客户端测试
 * @Author liforever
 * @Date 2019/3/28 21:15
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        redisTemplate.execute((RedisCallback<Object>) connection ->{
            connection.flushAll();
            return null;
        });
        assert redisTemplate.opsForValue().get("name") == null;
        redisTemplate.opsForValue().set("name","imooc");
        assert redisTemplate.opsForValue().get("name") != null;
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}

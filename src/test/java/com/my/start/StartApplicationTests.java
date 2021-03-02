package com.my.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.start.pojo.User;
import com.my.start.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class StartApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;
    @Test
    void contextLoads() {
    }

    @Test
    public void test() throws Exception{
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
        User strive = new User("strive", 10);
        String value = new ObjectMapper().writeValueAsString(strive);
        redisTemplate.opsForValue().set("user", value);

        System.out.println(redisTemplate.opsForValue().get("user"));
    }


    @Test
    public void test1(){
        redisUtil.set("a", "b");
        System.out.println(redisUtil.get("a"));
    }
}

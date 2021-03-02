package com.my.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.start.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class StartApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
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
}

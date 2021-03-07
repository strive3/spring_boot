package com.my.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.start.pojo.User;
import com.my.start.util.RedisUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@SpringBootTest
class StartApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisLockRegistry redisLockRegistry;

    @Autowired
    RestHighLevelClient restHighLevelClient;

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

    /**
     * @Author du-xp
     * @Date 2021/3/7

     * @return: void
     * @Description 分布式事务锁
     */
    @Test
    public void testLock(){
        User bravo = new User("bravo", 11);
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("bravo", bravo, 30, TimeUnit.SECONDS);
        if (lock){
            //业务代码

            redisTemplate.delete("bravo");
        }
    }


    @Test
    public void testRedisLock(){
        User user = new User();
        user.setUserName("g");
        user.setAge(12);
        String lockKey = "config" + user.getUserName();
        Lock lock = redisLockRegistry.obtain(lockKey);//获取锁
        try {
            lock.lock();    //加锁
            Set keys = redisTemplate.keys("*");
            System.out.println(keys);
            //业务代码
            redisUtil.set("ghjkl", "a");
        } catch (Exception e){

        } finally {
            lock.unlock();//释放锁

            Set keys = redisTemplate.keys("*");
            System.out.println(keys);
        }
    }


    /**
     * @Author du-xp
     * @Date 2021/3/6

     * @return: void
     * @Description 索引
     */
    @Test
    public void testCreateIndex() throws Exception{
        CreateIndexRequest request = new CreateIndexRequest("strive");
        CreateIndexResponse response = restHighLevelClient.indices().
                create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }
}

package com.my.start.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author duxiaopeng
 * @Date 2021/3/2 4:27 下午
 * @Description TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    private String userName;

    private int age;
}

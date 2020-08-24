package com.yiqiandewo;

import com.yiqiandewo.pojo.Employee;
import com.yiqiandewo.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

@SpringBootTest
class SpringBootThymeleafApplicationTests {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    IEmployeeService employeeService;

    @Test
    void contextLoads() {

        Set<String> keys = redisTemplate.keys("*");

        for (String k : keys) {
            System.out.println(k);
        }


    }

}

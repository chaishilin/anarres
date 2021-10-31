package com.csl.anarres;

import com.csl.anarres.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnarresApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        String content = "cals Main { cjsj  } ";
        String[] results = content.split("Main");//其实还是用正则匹配比较好
        System.out.println(results);
    }

}

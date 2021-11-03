package com.csl.anarres;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.service.UserService;
import com.csl.anarres.utils.NumberGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnarresApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Test
    void contextLoads() {
        for(int i = 0;i < 10;i++){
            String id = NumberGenerator.getIdFromTableId(TableIdEnum.LABEl);
            System.out.println(id);
        }

    }



}

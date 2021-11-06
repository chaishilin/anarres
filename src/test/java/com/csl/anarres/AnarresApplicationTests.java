package com.csl.anarres;

import com.csl.anarres.schedule.ProgramScheduleTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnarresApplicationTests {
    @Autowired
    private
    ProgramScheduleTask programScheduleTask;

    @Test
    void contextLoads() {
        try {
            Thread.sleep(10000);

        }catch (Exception e){
            System.out.println("线程坏了");
        }
    }



}

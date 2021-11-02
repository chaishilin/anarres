package com.csl.anarres;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.service.UserService;
import com.csl.anarres.utils.CMDUtils;
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
        String path = "D:\\java\\anarres\\target\\programs\\";
        String name = "java1635692785033";
        CMDUtils.execCMD("cd "+path+"&&"+"del "+name+".java");
        CMDUtils.execCMD("cd "+path+"&&"+"del "+name+".class");
    }



}

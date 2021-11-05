package com.csl.anarres;

import org.junit.jupiter.api.Test;

class AnarresApplicationTests {
    @Test
    void contextLoads() {
        String s = "def s(a):    s = 3";
        for(String ss:s.split("\\(")){
            System.out.println(ss);
        }
        System.out.println(s.split("\\(")[0].replace("def ",""));

    }



}

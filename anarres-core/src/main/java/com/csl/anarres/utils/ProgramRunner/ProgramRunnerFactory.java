package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.enums.SupportLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:22
 * @Description: 生产实现了ProgramRunner接口的类
 */
@Component
public class ProgramRunnerFactory {
    private final Map<String, ProgramRunner> programRunnerMap = new ConcurrentHashMap<>();
    @Autowired
    public ProgramRunnerFactory(Map<String, ProgramRunner> programRunnerMap){
        programRunnerMap.forEach(this.programRunnerMap::put);
    }

    public ProgramRunner getRunner(String language){
        return getRunner(SupportLanguage.valueOf(language));
    }

    public ProgramRunner getRunner(SupportLanguage language){
        switch (language){
            case python:
                return programRunnerMap.get("python");
            case java:
                return programRunnerMap.get("java");
            case golang:
                return programRunnerMap.get("golang");
            default:
                throw new RuntimeException("不支持的编程语言！");
        }

    }

}

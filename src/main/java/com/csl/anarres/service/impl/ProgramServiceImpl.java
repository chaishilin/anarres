package com.csl.anarres.service.impl;

import com.csl.anarres.config.runProgramConfig;
import com.csl.anarres.dto.ProgramResponseDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.CMDUtils;
import com.csl.anarres.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:59
 * @Description:
 */
@Service
public class ProgramServiceImpl implements ProgramService {
    @Autowired
    runProgramConfig runProgramConfig;
    @Override
    public ProgramResponseDto doProgram(ProgramEntity entity){
        ProgramResponseDto responseDto = new ProgramResponseDto();
        saveProgramToLocal(entity);
        runProgram(entity);
        responseDto.setResult(entity.getOutput());
        return responseDto;
    }

    public String saveProgramToLocal(ProgramEntity entity){
        if(!SupportLanguage.isInclude(entity.getLanguage())){
            throw new RuntimeException("不支持的语言类型");
        }

        String path = runProgramConfig.getPath();
        //todo path 读取springboot 配置获得
        //todo bat读取参数，自动运行，结果给stream流
        ClassUtils.genarateClassName(entity);
        ClassUtils.changeClassContent(entity,entity.getClassName());
        switch (SupportLanguage.valueOf(entity.getLanguage())){
            case java:
                path += entity.getClassName() +"."+SupportLanguage.java.getName();
                break;
        }

        try {
            Files.write(Paths.get(path), entity.getCode().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saveProgramToLocal";
    }



    public void runProgram(ProgramEntity entity){
        String path = runProgramConfig.getPath();
        CMDUtils.execCMD("cd "+path+"&&"+"javac "+entity.getClassName()+".java");
        String result = CMDUtils.execCMD("cd "+path+"&&"+"java "+entity.getClassName());
        entity.setOutput(result);
    }

    public String saveProgramToSql(ProgramEntity entity){
        return "saveProgramToSql";
    }








}

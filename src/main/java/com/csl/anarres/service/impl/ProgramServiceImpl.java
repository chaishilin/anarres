package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.config.runProgramConfig;
import com.csl.anarres.dto.ProgramResponseDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.exception.RunProgramException;
import com.csl.anarres.mapper.ProgramMapper;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.CMDUtils;
import com.csl.anarres.utils.ClassUtils;
import com.csl.anarres.utils.HashcodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:59
 * @Description:
 */
@Service
public class ProgramServiceImpl implements ProgramService {
    @Autowired
    runProgramConfig runProgramConfig;
    @Autowired
    ProgramMapper mapper;

    @Override
    public ProgramResponseDto doProgram(ProgramEntity entity) {
        ProgramResponseDto responseDto = new ProgramResponseDto();
        entity.setCodeMD5(HashcodeBuilder.getHashcode(entity.getCode()));

        saveProgramToLocal(entity);//临时将程序储存至本地
        runProgram(entity);//在本地运行程序，获得结果
        deleteProgram(entity);//删除临时储存的程序
        saveProgramToSql(entity);
        responseDto.setResult(entity.getOutput());

        return responseDto;
    }

    public String saveProgramToLocal(ProgramEntity entity) {
        if (!SupportLanguage.isInclude(entity.getLanguage())) {
            throw new RuntimeException("不支持的语言类型");
        }

        String path = runProgramConfig.getPath();
        ClassUtils.genarateClassName(entity);
        ClassUtils.changeClassContent(entity, entity.getClassName());
        switch (SupportLanguage.valueOf(entity.getLanguage())) {
            case java:
                path += entity.getClassName() + "." + SupportLanguage.java.getName();
                break;
        }

        try {
            Files.write(Paths.get(path), entity.getCode().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saveProgramToLocal";
    }


    public void runProgram(ProgramEntity entity) {
        String path = runProgramConfig.getPath();
        try {
            CMDUtils.execCMD("cd " + path + "&&" + "javac " + entity.getClassName() + ".java");
            String result = CMDUtils.execCMD("cd " + path + "&&" + "java " + entity.getClassName() + "  " + entity.getInput());
            entity.setOutput(result);
        } catch (Exception e) {
            entity.setOutput(e.getMessage());
        }
    }

    public void deleteProgram(ProgramEntity entity) {
        String path = runProgramConfig.getPath();
        try {
            CMDUtils.execCMD("cd " + path + "&&" + "del " + entity.getClassName() + ".java");
            CMDUtils.execCMD("cd " + path + "&&" + "del " + entity.getClassName() + ".class");
        } catch (RunProgramException e) {
            //删除失败说明并没有该文件，因此不做处理
        }
    }

    public void saveProgramToSql(ProgramEntity entity) {
        entity.setCreatetime(new Date());
        QueryWrapper<ProgramEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODEMD5", entity.getCodeMD5());
        queryWrapper.eq("CREATER_ID", entity.getCreaterId());
        List<ProgramEntity> programEntityList = mapper.selectList(queryWrapper);
        if (programEntityList.size() == 0) {
            mapper.insert(entity);
        }
    }


}

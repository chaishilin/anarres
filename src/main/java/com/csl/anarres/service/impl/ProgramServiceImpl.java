package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.ProgramMapper;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.CMDUtils;
import com.csl.anarres.utils.ProgramUtils;
import com.csl.anarres.utils.FileUtil;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    RunProgramConfig runProgramConfig;
    @Autowired
    ProgramMapper mapper;
    @Autowired
    FileUtil fileUtil;
    @Autowired
    ProgramUtils programUtils;

    @Override
    public List<ProgramEntity> programList(ProgramEntity entity) {
        return mapper.findProgramList(entity);
    }

    @Override
    public void doProgram(ProgramEntity entity) {
        saveProgramToLocal(entity);//临时将程序储存至本地
        runProgram(entity);//在本地运行程序，获得结果
        //fileUtil.deleteProgramFromTargetPath();//删除临时储存的程序
    }

    public String saveProgramToLocal(ProgramEntity entity) {
        if (!SupportLanguage.isInclude(entity.getLanguage())) {
            throw new RuntimeException("不支持的语言类型");
        }
        programUtils.genarateClassName(entity);
        String path = runProgramConfig.getPath();
        path += entity.getClassName() + SupportLanguage.valueOf(entity.getLanguage()).getSuffix();
        try {
            String codeToSave = programUtils.programWrapper(entity);
            fileUtil.saveToPath(path, codeToSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "saveProgramToLocal";
    }


    public void runProgram(ProgramEntity entity) {
        String path = runProgramConfig.getPath();
        try {
            String result = null;
            String fileName = entity.getClassName()+ SupportLanguage.valueOf(entity.getLanguage()).getSuffix();
            switch (SupportLanguage.valueOf(entity.getLanguage())) {
                case java:
                    CMDUtils.execCMD("cd " + path + "&&" + "javac " +fileName);
                    result = CMDUtils.execCMD("cd " + path + "&&" + "java " + entity.getClassName() + "  " + entity.getInput());
                    break;
                case python:
                    result = CMDUtils.execCMD("cd " + path + "&&" + "python " + fileName);
                    break;
            }
            entity.setOutput(result);
        } catch (Exception e) {
            entity.setOutput(e.getMessage());
        }
    }

    @Override
    public void deleteProgram(String programId) {
        ProgramEntity entity = mapper.selectById(programId);
        entity.setState("00");
        mapper.updateById(entity);
    }

    public String saveProgramToSql(ProgramEntity entity) {
        entity.setCodeMD5(HashcodeBuilder.getHashcode(entity.getCode()));
        entity.setContentMD5(HashcodeBuilder.getHashcode(entity.getContent()));
        entity.setCreateTime(new Date());
        QueryWrapper<ProgramEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE_MD5", entity.getCodeMD5());
        queryWrapper.eq("TITLE", entity.getTitle());
        queryWrapper.eq("CONTENT_MD5", entity.getContentMD5());
        queryWrapper.eq("CREATER_ID", entity.getCreaterId());
        List<ProgramEntity> programEntityList = mapper.selectList(queryWrapper);
        if (programEntityList.size() == 0) {
            entity.setCreateTime(new Date());
            entity.setLastModifiedTime(new Date());
            entity.setProgramId(NumberGenerator.getIdFromTableId(TableIdEnum.PROGRAM));
            if (entity.getState() == null || "".equals(entity.getState())) {
                entity.setState("01");
            }
            if (entity.getPublicState() == null || "".equals(entity.getPublicState())) {
                entity.setPublicState("01");
            }
            mapper.insert(entity);
            return entity.getProgramId();
        } else if (programEntityList.size() == 1) {
            ProgramEntity programEntity = programEntityList.get(0);
            programEntity.setState("01");
            mapper.updateById(programEntity);
            return programEntityList.get(0).getProgramId();
        } else {
            throw new RuntimeException("查到多条重复记录");
        }
    }


}

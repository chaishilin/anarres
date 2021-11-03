package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.ProgramMapper;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.CMDUtils;
import com.csl.anarres.utils.ClassUtils;
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

    @Override
    public void doProgram(ProgramEntity entity) {
        saveProgramToLocal(entity);//临时将程序储存至本地
        runProgram(entity);//在本地运行程序，获得结果
        fileUtil.deleteProgramFromTargetPath();//删除临时储存的程序
    }

    public String saveProgramToLocal(ProgramEntity entity)  {
        if (!SupportLanguage.isInclude(entity.getLanguage())) {
            throw new RuntimeException("不支持的语言类型");
        }
        ClassUtils.genarateClassName(entity);
        String path = runProgramConfig.getPath();
        switch (SupportLanguage.valueOf(entity.getLanguage())) {
            case java:
                path += entity.getClassName() + "." + SupportLanguage.java.getName();
                break;
        }

        try {
            String relativePath = runProgramConfig.getRelativePath();
            StringBuilder sStart = fileUtil.readFromClasspath(relativePath+"\\"+"SolutionStart.java");
            StringBuilder sEnd = fileUtil.readFromClasspath(relativePath+"\\"+"SolutionEnd.java");
            String codeToSave = sStart.append(entity.getCode()).append(sEnd).toString();
            fileUtil.saveToPath(path,codeToSave);
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

    public String saveProgramToSql(ProgramEntity entity) {
        entity.setCodeMD5(HashcodeBuilder.getHashcode(entity.getCode()));
        entity.setContentMD5(HashcodeBuilder.getHashcode(entity.getContent()));
        entity.setCreatetime(new Date());
        QueryWrapper<ProgramEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CODE_MD5", entity.getCodeMD5());
        queryWrapper.eq("TITLE", entity.getTitle());
        queryWrapper.eq("CONTENT_MD5", entity.getContentMD5());
        queryWrapper.eq("CREATER_ID", entity.getCreaterId());
        List<ProgramEntity> programEntityList = mapper.selectList(queryWrapper);
        if (programEntityList.size() == 0) {
            entity.setCreatetime(new Date());
            entity.setLastModifiedTime(new Date());
            entity.setProgramId(NumberGenerator.getIdFromTableId(TableIdEnum.PROGRAM));
            if(entity.getState() == null || "".equals(entity.getState())){
                entity.setState("01");
            }
            if(entity.getPublicState() == null || "".equals(entity.getPublicState())){
                entity.setPublicState("01");
            }
            mapper.insert(entity);
            return entity.getProgramId();
        }else if(programEntityList.size() == 1){
            return programEntityList.get(0).getProgramId();
        }else{
            throw new RuntimeException("查到多条重复记录");
        }
    }


}

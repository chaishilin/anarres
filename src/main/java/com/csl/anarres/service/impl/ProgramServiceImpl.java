package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.ProgramMapper;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.CMDUtils;
import com.csl.anarres.utils.FileUtil;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.NumberGenerator;
import com.csl.anarres.utils.ProgramUtils;
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
    private RunProgramConfig runProgramConfig;
    @Autowired
    private ProgramMapper mapper;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ProgramUtils programUtils;

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
                case golang:
                    result = CMDUtils.execCMD("cd " + path + "&&" + "go run " + fileName + " " + entity.getInput());
                    break;
                case python:
                    result = CMDUtils.execCMD("cd " + path + "&&" + "python " + fileName );
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
        entity.setState("01");
        entity.setPublicState("01");
        entity.setLastModifiedTime(new Date());

        //对发送主键的进行更新，对无主键的进行新增
        if (entity.getProgramId() == null || "".equals(entity.getProgramId())) {
            //无主键
            entity.setProgramId(NumberGenerator.getIdFromTableId(TableIdEnum.PROGRAM));
            entity.setCreateTime(new Date());
            mapper.insert(entity);
        } else {
            //有主键，更新
            mapper.updateById(entity);
        }
        return entity.getProgramId();
    }

    @Override
    public void hardDeleteProgram(){
        QueryWrapper<ProgramEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATE","00");
        queryWrapper.lt("LAST_MODIFIED_TIME",new Date());
        mapper.selectList(queryWrapper).forEach(p->{
            mapper.deleteById(p.getProgramId());
            System.err.println(p.getProgramId()+" delete");
        });
    }

}

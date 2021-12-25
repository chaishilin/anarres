package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.entity.ProgramInterface;
import com.csl.anarres.mapper.ProgramTemplateMapper;
import com.csl.anarres.utils.CMDUtils.CMDUtils;
import com.csl.anarres.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:11
 * @Description:
 */
@Component("java")
public class JavaRunner extends ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ProgramTemplateMapper templateMapper;

    private final String java = "java";
    private final String javac = "javac";

    @Override
    public String runCMD(ProgramRunnerDto dto) {
        cmdUtils.createInstance().execCMD(dto.getPath(), javac + " " + dto.getFileName());
        if (dto.getInput() == null) {
            return cmdUtils.createInstance().execCMD(dto.getPath(), java + " " + dto.getClassName());
        } else {
            return cmdUtils.createInstance().execCMD(dto.getPath(), java + " " + dto.getClassName() + "  " + dto.getInput());
        }
    }

    @Override
    public String simpleFunction() {
        return "public void func1({{params}}){\n" +
                "    }";
    }

    @Override
    protected String chooseTemplate() {
        return templateMapper.selectById("062021122000011").getTemplate();
    }

    @Override
    protected String chooseDeaultTemplate() {
        return templateMapper.selectById("062021122000011").getTemplate();
    }

    @Override
    public String getLanguage() {
        return "java";
    }

    @Override
    public String getFunctionName(ProgramInterface entity) {
        return entity.getCode().split("\\(")[0].replace("public ", "").split(" ")[1];
    }
}

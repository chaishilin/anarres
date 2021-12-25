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
@Component("python")
public class PythonRunner extends ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ProgramTemplateMapper templateMapper;

    private final String python = "python";

    @Override
    public String runCMD(ProgramRunnerDto dto) {
        return cmdUtils.createInstance().execCMD(dto.getPath(),python + " " + dto.getFileName());
    }

    @Override
    public String simpleFunction() {
        return "def func1({{params}}):\n" +
                "\treturn ";
    }
    @Override
    protected String chooseTemplate() {
        return templateMapper.selectById("062021121900025").getTemplate();
    }

    @Override
    protected String chooseDeaultTemplate() {
        return templateMapper.selectById("062021121900025").getTemplate();
    }

    @Override
    public String getLanguage() {
        return "python";
    }

    @Override
    public String getFunctionName(ProgramInterface entity){
        return entity.getCode().split("\\(")[0].replace("def ","");
    }

}

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
@Component("golang")
public class GolangRunner extends ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ProgramTemplateMapper templateMapper;

    private final String command = "go run ";
    @Override
    public String runCMD(ProgramRunnerDto dto) {
        if(dto.getInput() == null){
            return cmdUtils.createInstance().execCMD(dto.getPath(), command + dto.getFileName());
        }else{
            return cmdUtils.createInstance().execCMD(dto.getPath(), command + dto.getFileName() + " " + dto.getInput());
        }
    }

    @Override
    public String simpleFunction() {
        return null;
    }
    @Override
    protected String chooseTemplate() {
        return templateMapper.selectById("062021122000001").getTemplate();
    }

    @Override
    public String getLanguage() {
        return "golang";
    }

    @Override
    public String getFunctionName(ProgramInterface entity){
        return entity.getCode().split("\\(")[0].replace("func ","");
    }

}

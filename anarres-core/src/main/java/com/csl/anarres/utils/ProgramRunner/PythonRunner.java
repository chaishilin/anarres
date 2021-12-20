package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.entity.ProgramInterface;
import com.csl.anarres.entity.ProgramTemplateEntity;
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
    public String programWrapper(ProgramInterface entity) {
        //        ProgramTemplateEntity templateEntity = templateMapper.selectById(dto.getTemplateId());
        ProgramTemplateEntity templateEntity = templateMapper.selectById("062021121900025");
        String template = templateEntity.getTemplate();
        template = template.replace("{{FunctionBody}}",getFunctionBody(entity));
        template = template.replace("{{FunctionName}}",getFunctionName(entity));
        template = template.replace("{{Parameters}}",getParameters(entity));
        return template;
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

package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.entity.ProgramEntity;
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
public class PythonRunner implements ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;

    private final String python = "python";
    @Override
    public String run(ProgramRunnerDto dto) {
        return cmdUtils.createInstance().execCMD(dto.getPath(),python + " " + dto.getFileName());
    }

    @Override
    public String programWrapper(ProgramEntity entity) {
        String relativePath = runProgramConfig.getRelativePath();
        String code = entity.getCode();
        StringBuilder pythonTemplate = new StringBuilder();
        pythonTemplate.append(code);
        pythonTemplate.append("\n");
        pythonTemplate.append(fileUtil.readFromClasspath(relativePath + "\\" + "Solution.py"));
        String defName = code.split("\\(")[0].replace("def ","");
        StringBuilder runDefStr = new StringBuilder();
        //组成调用函数的那一行
        runDefStr.append(defName);
        runDefStr.append("(");
        if(entity.getInput() != null && !"".equals(entity.getInput())){
            runDefStr.append(entity.getInput().replace(" ",","));
        }
        runDefStr.append(")");
        runDefStr.append("\n");
        return pythonTemplate.toString().replace("inputYourFunction",runDefStr.toString());
    }
}

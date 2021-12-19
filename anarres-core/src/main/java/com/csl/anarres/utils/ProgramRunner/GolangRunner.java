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
@Component("golang")
public class GolangRunner extends ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;

    private final String command = "go run ";
    @Override
    public String runCMD(ProgramRunnerDto dto) {
        return cmdUtils.createInstance().execCMD(dto.getPath(), command + dto.getFileName() + " " + dto.getInput());
    }

    @Override
    public String programWrapper(ProgramEntity entity) {
        String relativePath = runProgramConfig.getRelativePath();
        String code = entity.getCode();
        String defName = code.split("\\(")[0].replace("func ","");

        StringBuilder goTemplate = fileUtil.readFromClasspath(relativePath + "\\" + "Solution.go");
        goTemplate.append(code);
        String goTemplateString = goTemplate.toString();
        goTemplateString = goTemplateString.replace("inputYourFunction",defName);

        StringBuilder inputStaff = new StringBuilder();
        inputStaff.append("fmt.Println(\"Call Function : ").append(defName).append("\")\n");
        inputStaff.append("fmt.Println(\"--------\")\n");
        goTemplateString = goTemplateString.replace("inputStaff",inputStaff.toString());

        StringBuilder outputStaff = new StringBuilder();
        outputStaff.append("fmt.Println(\"--------\")\n");
        outputStaff.append("fmt.Println(\"output:\")\n");
        goTemplateString = goTemplateString.replace("outputStaff",outputStaff.toString());

        return goTemplateString;
    }
}

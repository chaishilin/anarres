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
@Component("java")
public class JavaRunner extends ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;

    private final String java = "java";
    private final String javac = "javac";
    @Override
    public String runCMD(ProgramRunnerDto dto) {
        cmdUtils.createInstance().execCMD(dto.getPath(),javac +" " + dto.getFileName());
        return cmdUtils.createInstance().execCMD(dto.getPath(), java+" " + dto.getClassName() + "  " + dto.getInput());
    }

    @Override
    public String programWrapper(ProgramEntity entity) {
        String relativePath = runProgramConfig.getRelativePath();
        String code = entity.getCode();
        //todo 重写一下solution.JAVA 合并在一个文件中
        StringBuilder sStart = fileUtil.readFromClasspath(relativePath + "\\" + "SolutionStart.java");
        StringBuilder sEnd = fileUtil.readFromClasspath(relativePath + "\\" + "SolutionEnd.java");
        return sStart.append(code).append(sEnd).toString();
    }

    @Override
    public String getLanguage() {
        return "java";
    }
}

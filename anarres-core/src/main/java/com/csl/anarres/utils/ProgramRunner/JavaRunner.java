package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.utils.CMDUtils.CMDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:11
 * @Description:
 */
@Component("java")
public class JavaRunner implements ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    private final String java = "java";
    private final String javac = "javac";
    @Override
    public String run(ProgramRunnerDto dto) {
        cmdUtils.createInstance().execCMD(dto.getPath(),javac +" " + dto.getFileName());
        return cmdUtils.createInstance().execCMD(dto.getPath(), java+" " + dto.getClassName() + "  " + dto.getInput());
    }
}

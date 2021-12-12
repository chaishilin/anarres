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
@Component("python")
public class PythonRunner implements ProgramRunner {
    @Autowired
    private CMDUtils cmdUtils;
    private final String python = "python";
    @Override
    public String run(ProgramRunnerDto dto) {
        return cmdUtils.createInstance().execCMD(dto.getPath(),python + " " + dto.getFileName());
    }
}

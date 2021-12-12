package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.dto.ProgramRunnerDto;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:10
 * @Description: 不同编程语言的启动接口，提供run方法
 */
public interface ProgramRunner {
    String run(ProgramRunnerDto dto);
}

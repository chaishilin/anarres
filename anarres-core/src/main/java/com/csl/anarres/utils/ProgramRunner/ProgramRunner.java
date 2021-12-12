package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.entity.ProgramEntity;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:10
 * @Description: 不同编程语言的启动接口，提供run方法
 */
public interface ProgramRunner {

    /**
     * 运行程序
     * @param dto
     * @return
     */
    String run(ProgramRunnerDto dto);

    /**
     * 根据用户输入code，组装成真正能运行的程序文件
     * @param entity
     * @return
     */
    String programWrapper(ProgramEntity entity);
}

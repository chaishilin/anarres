package com.csl.anarres.service;

import com.csl.anarres.dto.ProgramResponseDto;
import com.csl.anarres.entity.ProgramEntity;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:57
 * @Description:远程运行程序
 */
public interface ProgramService {
    /**
     * 运行程序，返回结果，可以考虑采用模板方法来制作，doProgram为实际方法
     * @param entity
     * @return
     */
    ProgramResponseDto doProgram(ProgramEntity entity);

    /**
     * 负责将程序存入本地，供执行使用
     * @param entity
     * @return
     */
    String saveProgramToLocal(ProgramEntity entity);

    /**
     * 存入数据库，生成程序的md5签名，作者，程序内容
     * @param entity
     * @return
     */
    String saveProgramToSql(ProgramEntity entity);

}

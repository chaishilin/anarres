package com.csl.anarres.service;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramEntity;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:57
 * @Description:远程运行程序
 */
public interface ProgramService {

    List<ProgramDto> programList(ProgramEntity entity);
    /**
     * 运行程序，返回结果，可以考虑采用模板方法来制作，doProgram为实际方法
     * @param dto
     * @return
     */
    void doProgram(ProgramDto dto);

    /**
     * 存入数据库，生成程序的md5签名，作者，程序内容
     * @param dto
     * @return
     */
    String saveProgramToSql(ProgramDto dto);

    /**
     * 通过设置state为00来实现软删除
     * @param dto
     * @return
     */
    void deleteProgram(ProgramDto dto);

    /**
     * 定时任务 硬删除程序
     */
    void hardDeleteProgram();

}

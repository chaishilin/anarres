package com.csl.anarres.service;

import com.csl.anarres.entity.ProgramEntity;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:57
 * @Description:远程运行程序
 */
public interface ProgramService {

    List<ProgramEntity> programList(ProgramEntity entity);
    /**
     * 运行程序，返回结果，可以考虑采用模板方法来制作，doProgram为实际方法
     * @param entity
     * @return
     */
    void doProgram(ProgramEntity entity);

    /**
     * 负责将程序存入本地，供执行使用
     * @param entity
     * @return
     */
    String saveProgramToLocal(ProgramEntity entity) throws FileNotFoundException;

    /**
     * 存入数据库，生成程序的md5签名，作者，程序内容
     * @param entity
     * @return
     */
    String saveProgramToSql(ProgramEntity entity);

    /**
     * 通过设置state为00来实现软删除
     * @param programId
     * @return
     */
    void deleteProgram(String programId);

    /**
     * 定时任务 硬删除程序
     */
    void hardDeleteProgram();

}

package com.csl.anarres.interfaces;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:10
 * @Description:
 */
public interface ProcessAble {
    /**
     * 进行任务
     */
    void doProcess() throws Exception;
    /**
     * @return 获得当前任务进度（0~100）
     */
    String getProcess();

}

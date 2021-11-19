package com.csl.anarres.service.impl;

import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.service.ProgramService;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/2 15:37
 * @Description:
 */
public class ProgramRunnable implements Runnable {
    private ProgramEntity entity;
    private ProgramService programService;
    public ProgramRunnable(ProgramEntity entity,ProgramService programService){
        this.entity = entity;
        this.programService = programService;
    }
    @Override
    public void run() {
        System.out.println("create new thread to run program");
        programService.doProgram(this.entity);
        this.entity.setReadable(true);
    }
}

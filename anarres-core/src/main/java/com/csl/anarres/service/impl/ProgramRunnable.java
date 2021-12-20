package com.csl.anarres.service.impl;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.service.ProgramService;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/2 15:37
 * @Description:
 */
public class ProgramRunnable implements Runnable {
    private ProgramDto dto;
    private ProgramService programService;

    /**
     * 构造函数传参，因为ProgramRunnable是new出来的，@Autowired是失效的
     * @param dto
     * @param programService
     */
    public ProgramRunnable(ProgramDto dto, ProgramService programService){
        this.dto = dto;
        this.programService = programService;
    }
    @Override
    public void run() {
        System.out.println("create new thread to run program");
        programService.doProgram(this.dto);
        this.dto.setReadable(true);
    }
}

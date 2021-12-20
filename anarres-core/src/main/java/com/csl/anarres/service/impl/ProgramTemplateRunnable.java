package com.csl.anarres.service.impl;

import com.csl.anarres.websocket.TestTemplate;
import lombok.SneakyThrows;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/20 17:25
 * @Description:
 */
public class ProgramTemplateRunnable implements Runnable {
    private TestTemplate testTemplate;
    public ProgramTemplateRunnable(TestTemplate testTemplate){
        this.testTemplate = testTemplate;
    }

    @SneakyThrows
    @Override
    public void run() {
        for(int i = 1; i <= 100;i++){
            Thread.sleep(50);
            testTemplate.boardCastMsg(i+"");
        }
    }
}

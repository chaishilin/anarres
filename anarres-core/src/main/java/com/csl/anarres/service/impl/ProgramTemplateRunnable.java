package com.csl.anarres.service.impl;

import com.csl.anarres.websocket.TemplateTestWS;
import lombok.SneakyThrows;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/20 17:25
 * @Description:
 */
public class ProgramTemplateRunnable implements Runnable {
    private TemplateTestWS templateTestWS;
    public ProgramTemplateRunnable(TemplateTestWS templateTestWS){
        this.templateTestWS = templateTestWS;
    }

    @SneakyThrows
    @Override
    public void run() {
        templateTestWS.run();
    }
}

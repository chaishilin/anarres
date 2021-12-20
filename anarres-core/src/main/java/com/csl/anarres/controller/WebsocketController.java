package com.csl.anarres.controller;

import com.csl.anarres.service.impl.ProgramTemplateRunnable;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import com.csl.anarres.websocket.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/20 16:50
 * @Description:
 */
@RestController
public class WebsocketController {
    @Autowired
    private TestTemplate tt;

    @PostMapping("/hello")
    public ResponseTemplate hello(@RequestBody Map<String,Object> map) {
        try {
            String msg = (String) map.get("msg");
            tt.boardCastMsg(msg);
            return ResponseUtil.success(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("发送失败" + e.getMessage());
        }
    }

    /**
     * 进行模板测试
     * @param map
     * @return
     */
    @PostMapping("/doTest")
    public ResponseTemplate doTest(@RequestBody Map<String,Object> map) {
        try {
            //step 1 开启异步任务
                // step 1.1 运行异步任务
                // step 1.2 每运行一个，向ws中更新信息
            //step 2 返回成功
            Thread templateTest = new Thread(new ProgramTemplateRunnable(tt));
            templateTest.start();

            return ResponseUtil.success("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("发送失败" + e.getMessage());
        }
    }
}

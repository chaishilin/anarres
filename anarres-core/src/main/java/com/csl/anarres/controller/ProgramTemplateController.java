package com.csl.anarres.controller;

import com.csl.anarres.annotation.PaserUserState;
import com.csl.anarres.dto.ProgramTemplateDto;
import com.csl.anarres.entity.ProgramTemplateEntity;
import com.csl.anarres.service.ProgramTemplateService;
import com.csl.anarres.service.impl.ProgramTemplateRunnable;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import com.csl.anarres.websocket.TemplateTestWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/13 9:26
 * @Description:
 */
@RestController
@RequestMapping("/programTemplate")
public class ProgramTemplateController {
    @Autowired
    private ProgramTemplateService service;
    @Autowired
    private TemplateTestWS templateTestWS;
    @RequestMapping("/list")
    public ResponseTemplate programTemplateList(@RequestBody ProgramTemplateEntity entity, HttpServletRequest request) {
        try {
            List<ProgramTemplateEntity> result = service.select(entity);
            return ResponseUtil.success("", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板列表查询失败" + e.getMessage());
        }
    }

    @RequestMapping("/save")
    public ResponseTemplate update(@RequestBody ProgramTemplateEntity entity, HttpServletRequest request) {
        try {
            String result = service.save(entity);
            return ResponseUtil.success("程序模板列表保存成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板列表保存失败" + e.getMessage());
        }
    }

    @RequestMapping("/delete")
    public ResponseTemplate delete(@RequestBody ProgramTemplateEntity entity, HttpServletRequest request) {
        try {
            String result = service.softDelete(entity);
            return ResponseUtil.success("程序模板列表删除成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板列表删除失败" + e.getMessage());
        }
    }

    @RequestMapping("runTemplate")
    public ResponseTemplate runTemplate(@RequestBody ProgramTemplateEntity entity, HttpServletRequest request) {
        try {
            String result = service.runTemplate(entity);
            return ResponseUtil.success("程序模板列表测试成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板列表测试失败" + e.getMessage());
        }
    }


    @PostMapping("testTemplate")
    public ResponseTemplate doTest(@PaserUserState ProgramTemplateDto dto, HttpServletRequest request){
        try {
            //step 1 开启异步任务
            // step 1.1 运行异步任务
            // step 1.2 每运行一个，向ws中更新信息
            //step 2 返回成功
            Thread templateTest = new Thread(new ProgramTemplateRunnable(templateTestWS,dto));
            //异步启动线程
            templateTest.start();
            return ResponseUtil.success("ok","ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("发送失败" + e.getMessage());
        }
    }


}

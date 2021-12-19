package com.csl.anarres.controller;

import com.csl.anarres.entity.ProgramTemplateEntity;
import com.csl.anarres.service.ProgramTemplateService;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

}

package com.csl.anarres.controller;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.DateTypeEntity;
import com.csl.anarres.exception.RunProgramException;
import com.csl.anarres.service.DataTypeService;
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
 * @Date: 2021/12/22 14:45
 * @Description:
 */
@RestController
@RequestMapping(value = "/dataType")
public class DataTypeController {
    @Autowired
    private DataTypeService service;

    @RequestMapping(value = "/list")
    public ResponseTemplate list(@RequestBody DateTypeEntity entity, HttpServletRequest request) {
        try {
            List<DateTypeEntity> result = service.select(entity);
            return ResponseUtil.success("程序模板测试用例查询成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板测试用例查询失败" + e.getMessage());
        }
    }

    @RequestMapping(value = "/save")
    public ResponseTemplate update(@RequestBody DateTypeEntity entity, HttpServletRequest request) {
        try {
            entity.setState("02");
            String result = service.save(entity);
            return ResponseUtil.success("程序模板测试用例保存成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板测试用例保存失败" + e.getMessage());
        }
    }

    @RequestMapping(value = "/delete")
    public ResponseTemplate delete(@RequestBody DateTypeEntity entity, HttpServletRequest request) {
        try {
            String result = service.softDelete(entity);
            return ResponseUtil.success("程序模板测试用例删除成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板测试用例删除失败" + e.getMessage());
        }
    }

    @RequestMapping(value = "/enable")
    public ResponseTemplate enable(@RequestBody DateTypeEntity entity, HttpServletRequest request) {
        try {
            //step1 : run
            ProgramDto testResult = service.run(entity);
            if(testResult.isError()){
                //如果报错，则报错
                throw new RunProgramException(testResult.getOutput());
            }
            //step2 : save
            entity.setState("01");
            String result = service.save(entity);
            return ResponseUtil.success("程序模板测试用例启用成功", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序模板测试用例启用失败" + e.getMessage());
        }
    }

}

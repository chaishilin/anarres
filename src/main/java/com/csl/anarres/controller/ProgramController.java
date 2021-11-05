package com.csl.anarres.controller;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.LoginService;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.service.impl.ProgramRunnable;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:00
 * @Description:
 */
@RestController
public class ProgramController {
    @Autowired
    ProgramService programService;
    @Autowired
    RunProgramConfig runProgramConfig;
    @Autowired
    LoginService loginService;
    @RequestMapping("/programList")
    public ResponseTemplate programList(@RequestBody ProgramEntity entity,HttpServletRequest request) {
        try {
            List<ProgramEntity> result = programService.programList(entity);
            return ResponseUtil.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail("程序保存失败"+e.getMessage());
        }
    }
    @PostMapping("/saveProgram")
    public ResponseTemplate saveProgram(@RequestBody ProgramEntity entity,HttpServletRequest request) {
        try {
            UserEntity user = loginService.getUserInfo(request);
            entity.setCreaterId(user.getUserId());
            String id = programService.saveProgramToSql(entity);
            return ResponseUtil.success(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail("程序保存失败"+e.getMessage());
        }
    }
    @PostMapping("/deleteProgram")
    public ResponseTemplate deleteProgram(@RequestBody Map<String,String> map, HttpServletRequest request) {
        try {
            String programId = map.get("programId");
            programService.deleteProgram(programId);
            return ResponseUtil.success(programId);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail("程序删除失败"+e.getMessage());
        }
    }
    @PostMapping("/doRemoteProgram")
    public ResponseTemplate doRemoteProgram(@RequestBody ProgramEntity entity,HttpServletRequest request){
        try{
            UserEntity user = loginService.getUserInfo(request);
            entity.setCreaterId(user.getUserId());
            Thread t = new Thread(new ProgramRunnable(entity,programService));
            t.start();
            long now =System.currentTimeMillis();
            while(entity.isReadable() == false){
                Thread.sleep(50);
                if(System.currentTimeMillis() - now > runProgramConfig.getTimeout()){
                    t.interrupt();
                    break;
                }
            }
            ProgramDto result = new ProgramDto();
            if(entity.isReadable()){
                result.setResult(entity.getOutput());
            }else{
                result.setResult("程序超时！");
            }
            return ResponseUtil.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail(e.getMessage());
        }
    }
}

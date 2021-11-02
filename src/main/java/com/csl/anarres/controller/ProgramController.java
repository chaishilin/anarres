package com.csl.anarres.controller;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
            ProgramResponseDto result = new ProgramResponseDto();
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

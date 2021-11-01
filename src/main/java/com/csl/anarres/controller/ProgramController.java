package com.csl.anarres.controller;

import com.csl.anarres.dto.ProgramResponseDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:00
 * @Description:
 */
@RestController
public class ProgramController {
    @Autowired
    ProgramService programService;

    @PostMapping("/doRemoteProgram")
    public ResponseTemplate doRemoteProgram(@RequestBody ProgramEntity entity){
        try{
            ProgramResponseDto result = this.programService.doProgram(entity);
            return ResponseUtil.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail(e.getMessage());
        }
    }
}

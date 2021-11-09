package com.csl.anarres.controller;

import com.csl.anarres.annotation.UserSelfOnly;
import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.service.LoginService;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.service.impl.ProgramRunnable;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private ProgramService programService;
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/programList")
    public ResponseTemplate programList(@UserSelfOnly ProgramEntity entity, HttpServletRequest request) {
        try {
            //@UserSelfOnly 注解的方式自动装填entity.createrId
            List<ProgramDto> result = programService.programList(entity);
            return ResponseUtil.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail("程序列表查询失败"+e.getMessage());
        }
    }
    @RequestMapping("/publicProgramList")
    public ResponseTemplate publicProgramList() {
        try {
            ProgramEntity entity = new ProgramEntity();
            entity.setPublicState("01");
            List<ProgramDto> result = programService.programList(entity);
            return ResponseUtil.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail("公共列表查询失败"+e.getMessage());
        }
    }
    @PostMapping("/saveProgram")//todo 保存程序，定时任务的硬删除程序 都需要针对数据库的变动进行修改
    public ResponseTemplate saveProgram(@UserSelfOnly ProgramDto dto,HttpServletRequest request) {
        try {
            //对于这种需要写库的操作，需要幂等性接口,防止频繁写库
            String entityMD5 = HashcodeBuilder.getHashcode(dto.toString());
            Jedis jedis = RedisUtil.getInstance();
            if(jedis.get(entityMD5) == null){
                //如果是第一次请求
                jedis.setex(entityMD5,(long)10,"1");//生成2s过时的主键，表明请求已在执行
                String id = programService.saveProgramToSql(dto);
                //生成程序的主键id后,前端再次请求后会带上这个id,这样保证重复点击时只有两次会请求到数据库。
                return ResponseUtil.success("保存成功",id);
            }else{
                jedis.setex(entityMD5,(long)10,"1");//如果重复请求，就再延长2s
                return ResponseUtil.success("请不要频繁点击",dto.getProgramId());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.fail("程序保存失败"+e.getMessage());
        }
    }
    @PostMapping("/deleteProgram")
    public ResponseTemplate deleteProgram(@UserSelfOnly Map<String,String> map, HttpServletRequest request) {
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
    public ResponseTemplate doRemoteProgram(@UserSelfOnly ProgramEntity entity,HttpServletRequest request){
        try{
            Thread t = new Thread(new ProgramRunnable(entity,programService));
            t.start();
            long now =System.currentTimeMillis();
            while(!entity.isReadable()){
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

    @RequestMapping("/supportLanguageList")
    public ResponseTemplate supportLanguageList(){
        List<String> result = new ArrayList<>();
        for(SupportLanguage language : SupportLanguage.values()){
            result.add(language.getName());
        }
        return ResponseUtil.success(result);
    }
}

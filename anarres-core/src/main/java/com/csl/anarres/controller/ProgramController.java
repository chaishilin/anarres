package com.csl.anarres.controller;

import com.csl.anarres.annotation.IdempotenceRequest;
import com.csl.anarres.annotation.PaserUserState;
import com.csl.anarres.annotation.RemoveCache;
import com.csl.anarres.annotation.RequestFrequency;
import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.service.LoginService;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.service.impl.ProgramRunnable;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    Logger logger = LoggerFactory.getLogger(ProgramController.class);

    @RequestFrequency(value = 5)//对于请求，限制请求频率
    @RequestMapping("/programList")
    public ResponseTemplate programList(@PaserUserState ProgramDto dto, HttpServletRequest request) {
        try {
            //@PaserUserState 注解的方式装填dto中的是否登录和是否本人
            List<ProgramDto> result = null;
            String msg = null;
            ProgramEntity entity = new ProgramEntity();
            BeanUtils.copyProperties(dto, entity);
            if (dto.getProgramId() != null && !"".equals(dto.getProgramId())) {
                //查看具体程序不需要校验是否登录或者本人
                entity.setCreaterId(null);
                result = programService.programList(entity);
                msg = "具体程序";
            } else if (dto.isLogin() && dto.isSelf()) {
                //如果是登录并且是本人，那么返回本人的列表
                result = programService.programList(entity);
                msg = "个人程序清单";
            } else {
                //如果没有登录，或者请求的createrId是public,就返回公共列表
                entity.setPublicState("01");

                result = programService.programList(entity);
                msg = "公共程序清单";
            }
            return ResponseUtil.success(msg, result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序列表查询失败" + e.getMessage());
        }
    }

    @RequestFrequency(value = 2)//对于请求，限制请求频率
    @IdempotenceRequest(requestMethod = "{userId}saveProgram")
    @RemoveCache(requestMethod = "{userId}programList")
    @PostMapping("/saveProgram")//todo 保存程序，定时任务的硬删除程序 都需要针对数据库的变动进行修改
    public ResponseTemplate saveProgram(@PaserUserState ProgramDto dto, HttpServletRequest request) {
        try {
            if (!dto.isLogin()) {
                throw new RuntimeException("未登录");
            }
            if (!dto.isSelf()) {
                throw new RuntimeException("不是本人的程序，不能保存！");
            }
            String id = programService.saveProgramToSql(dto);
            //生成程序的主键id后,前端再次请求后会带上这个id,这样保证重复点击时只有两次会请求到数据库。
            logger.info("保存程序,id:" + id + " 成功");
            return ResponseUtil.success("保存成功", id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序保存失败" + e.getMessage());
        }
    }

    @PostMapping("/deleteProgram")
    @RemoveCache(requestMethod = "{userId}programList")
    public ResponseTemplate deleteProgram(@PaserUserState ProgramDto dto, HttpServletRequest request) {
        try {
            if (!dto.isLogin()) {
                throw new RuntimeException("未登录");
            }
            if (!dto.isSelf()) {
                throw new RuntimeException("不是本人的程序，不能删除！");
            }
            programService.deleteProgram(dto);
            return ResponseUtil.success(dto.getProgramId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail("程序删除失败" + e.getMessage());
        }
    }

    @RequestFrequency(value = 2)//对于请求，限制请求频率
    @IdempotenceRequest(requestMethod = "{userId}doRemoteProgram")//对于调用服务器资源的操作，需要幂等性接口,防止频繁占用资源
    @PostMapping("/doRemoteProgram")
    public ResponseTemplate doRemoteProgram(@RequestBody ProgramEntity entity, HttpServletRequest request) {
        try {
            Thread t = new Thread(new ProgramRunnable(entity, programService));
            t.start();
            long now = System.currentTimeMillis();
            while (!entity.isReadable()) {
                Thread.sleep(50);
                if (System.currentTimeMillis() - now > runProgramConfig.getTimeout()) {
                    t.interrupt();
                    break;
                }
            }
            ProgramDto result = new ProgramDto();
            if (entity.isReadable()) {
                result.setResult(entity.getOutput());
            } else {
                result.setResult("程序超时！");
            }
            if(entity.isError()){
                //如果程序报错
                //todo 红色展示报错信息，总之要返回非200的状态码
                return ResponseUtil.success(result);
            }else{
                return ResponseUtil.success(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @IdempotenceRequest(requestMethod = "{userId}supportLanguageList")
    @RequestMapping("/supportLanguageList")
    public ResponseTemplate supportLanguageList() {
        List<String> result = new ArrayList<>();
        for (SupportLanguage language : SupportLanguage.values()) {
            result.add(language.getName());
        }
        return ResponseUtil.success(result);
    }
}

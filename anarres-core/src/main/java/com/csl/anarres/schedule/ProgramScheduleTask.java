package com.csl.anarres.schedule;

import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/6 17:35
 * @Description:
 */

@EnableScheduling
@Configuration
public class ProgramScheduleTask {
    @Autowired
    private ProgramService programService;

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨执行一次
    public void deleteProgram() {
        if (!"null".equals(RedisUtil.getInstance().get("deleteProgram"))){
            //如果没有线程正在删除，那么就删除
            System.err.println("开始删除"+new Date());
            RedisUtil.getInstance().setex(HashcodeBuilder.getHashcode("deleteProgram"), (long)1,"1");
            //设置正在删除
            programService.hardDeleteProgram();
            RedisUtil.getInstance().del("deleteProgram");//程序结束后取消置位
            System.err.println("删除结束");
        }

    }
}

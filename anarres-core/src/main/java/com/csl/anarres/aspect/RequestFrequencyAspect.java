package com.csl.anarres.aspect;

import com.csl.anarres.annotation.RequestFrequency;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.JoinPointUtil;
import com.csl.anarres.utils.LoginUtil;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 19:11
 * @Description:
 */

@Order(2)
@Aspect
@Component
public class RequestFrequencyAspect{
    private Logger logger = LoggerFactory.getLogger(RequestFrequencyAspect.class);
    private Jedis jedis = RedisUtil.getInstance();
    @Autowired
    private LoginUtil loginUtil;



    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..)) && @annotation(com.csl.anarres.annotation.RequestFrequency)")
    public void requestFrenquency() {
    }//只是个函数签名，帮助记录的

    @Around("requestFrenquency()")
    public Object Interceptor(ProceedingJoinPoint joinPoint){
        MethodSignature msg = (MethodSignature) joinPoint.getSignature();
        String methodName = msg.getMethod().getName();
        logger.info(methodName+" 使用注解：RequestFrequency");
        String userId = loginUtil.getCurrentUserOrPublic().getUserId();//利用token获得userEntity
        String userMethodMD5 = HashcodeBuilder.getHashcode(userId+methodName);
        int requestFrequencyTime = msg.getMethod().getAnnotation(RequestFrequency.class).value();
        if(jedis.get(userMethodMD5) == null){
            //如果最近没有请求过
            return JoinPointUtil.doRequestCacheInKey(joinPoint, userMethodMD5, requestFrequencyTime);//则进行请求
        }else{
            //否则报错，避免频繁请求
            return ResponseUtil.fail("不要频繁请求");
        }
    }
}

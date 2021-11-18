package com.csl.anarres.annotation;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.JoinPointUtil;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 19:11
 * @Description:
 */
@Order(1)
@Aspect
@Component
public class IdempotenceRequestAspect {
    Logger logger = LoggerFactory.getLogger(IdempotenceRequestAspect.class);
    Jedis jedis = RedisUtil.getInstance();

    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..)) && @annotation(com.csl.anarres.annotation.IdempotenceRequest)")
    public void validRequest() {
    }//只是个函数签名，帮助记录的

    @Around("validRequest()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature msg = (MethodSignature) joinPoint.getSignature();
        int idempotenceRequestTime = msg.getMethod().getAnnotation(IdempotenceRequest.class).value();
        Object[] args = joinPoint.getArgs();
        String sign = joinPoint.getSignature().getName();
        logger.info(sign+" 使用注解：IdempotenceRequest");
        String argMD5 = null;
        if (args.length >= 1) {
            //如果请求是有参数的,md5为参数+方法
            argMD5 = HashcodeBuilder.getHashcode(args[0].toString() + sign);
        } else {
            //否则，md5为方法
            argMD5 = HashcodeBuilder.getHashcode(sign);
        }
        String response = jedis.get(argMD5);
        if (response != null) {
            //如果最近已经请求过，那么直接返回请求结果
            try {
                logger.info("从缓存中直接返回请求:" + joinPoint.getSignature());
                return JSONObject.toJavaObject(JSONObject.parseObject(response), ResponseTemplate.class);
            } catch (Exception e) {
                //如果从缓存的数据转换失败，则进行请求，并将响应计入缓存
                return JoinPointUtil.doRequestWithArg(joinPoint, argMD5, idempotenceRequestTime);
            }
        } else {
            //如果没有，则进行请求，并将响应计入缓存
            return JoinPointUtil.doRequestWithArg(joinPoint, argMD5, idempotenceRequestTime);
        }
    }
}

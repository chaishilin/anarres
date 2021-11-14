package com.csl.anarres.annotation;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 19:11
 * @Description:
 */

@Aspect
@Component
public class IdempotenceRequestAspect {
    Logger logger = LoggerFactory.getLogger(IdempotenceRequestAspect.class);
    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..)) && @annotation(com.csl.anarres.annotation.IdempotenceRequest)")
    public void validRequest(){
    }//只是个函数签名，帮助记录的

    @Around("validRequest()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Object[] args = joinPoint.getArgs();
        if(args.length >= 1){
            //如果请求是有参数的
            Jedis jedis = RedisUtil.getInstance();
            String argMD5 = HashcodeBuilder.getHashcode(args[0].toString());
            System.out.println(argMD5);
            String response = jedis.get(argMD5);
            if(response != null){
                //如果最近已经请求过，那么直接返回请求结果
                logger.info("从缓存中直接返回请求:"+joinPoint.getSignature());
                return JSONObject.toJavaObject(JSONObject.parseObject(response),ResponseTemplate.class);
            }else{
                //如果没有，则进行请求，并将响应计入缓存
                try {
                    result = joinPoint.proceed();
                    jedis.setex(argMD5,(long)20,JSONObject.toJSONString(result));
                }catch (Exception e){
                    e.printStackTrace();;
                }
            }
        }
        return result;
    }
}

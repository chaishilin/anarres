package com.csl.anarres.aspect;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.annotation.IdempotenceRequest;
import com.csl.anarres.utils.JoinPointUtil;
import com.csl.anarres.utils.LoginUtil;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseTemplate;
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
@Order(1)//首先判断是否为相同内容的频繁请求
@Aspect
@Component//todo redis挂了怎么办
public class IdempotenceRequestAspect {
    Logger logger = LoggerFactory.getLogger(IdempotenceRequestAspect.class);
    Jedis jedis = RedisUtil.getInstance();
    @Autowired
    private LoginUtil loginUtil;
    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..)) && @annotation(com.csl.anarres.annotation.IdempotenceRequest)")
    public void validRequest() {
    }//只是个函数签名，帮助记录的

    @Around("validRequest()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature msg = (MethodSignature) joinPoint.getSignature();
        int requestTime = msg.getMethod().getAnnotation(IdempotenceRequest.class).requestTime();
        String requestMethod = msg.getMethod().getAnnotation(IdempotenceRequest.class).requestMethod();
        String userId = loginUtil.getCurrentUserOrPublic().getUserId();
        String key = requestMethod.replace("{userId}",userId);
        Object[] args = joinPoint.getArgs();
        String field = args.length >=1 ? JSONObject.toJSONString(args[0]):"{}";//如果请求是有参数的,则设置key为请求参数
        String response = jedis.hget(key,field);
        if (response != null) {
            try {
                logger.info(key+" 使用注解：IdempotenceRequest 读取到缓存");
                return JSONObject.toJavaObject(JSONObject.parseObject(response), ResponseTemplate.class);
            } catch (Exception e) {
                //如果从缓存的数据转换失败，则进行请求，并将响应计入缓存
                return JoinPointUtil.doRequestCacheInHset(joinPoint, key,field, requestTime);
            }
        } else {
            //如果没有，则进行请求，并将响应计入缓存
            return JoinPointUtil.doRequestCacheInHset(joinPoint, key,field, requestTime);
        }
    }
}

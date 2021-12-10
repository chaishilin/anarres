package com.csl.anarres.aspect;

import com.csl.anarres.annotation.RequestFrequency;
import com.csl.anarres.utils.JoinPointUtil;
import com.csl.anarres.utils.LoginUtil;
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
        int requestFrequency = msg.getMethod().getAnnotation(RequestFrequency.class).value();
        Object result = JoinPointUtil.doRequestWithFrenquencyLimit(joinPoint, methodName+userId, requestFrequency);
        //如果llen的长度等于请求次数，看llen的第一个是不是超过了一秒钟，如果是，那就移除第一个，并且继续请求
        if(result == null){
            //报错，避免频繁请求
            return ResponseUtil.fail("不要频繁请求");
        }
        return result;
    }
}

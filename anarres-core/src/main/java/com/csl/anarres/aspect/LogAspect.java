package com.csl.anarres.aspect;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.utils.ArgsUtil;
import com.csl.anarres.utils.JoinPointUtil;
import com.csl.anarres.utils.LoginUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/22 16:32
 * @Description:
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private LoginUtil loginUtil;
    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..))")
    public void webLog() {
    }//只是个函数签名，帮助记录的

    @Around("webLog()")
    public Object writeWebLog(ProceedingJoinPoint joinPoint) {
        Object result = JoinPointUtil.doRequest(joinPoint);
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        JSONObject logMap = new JSONObject();
        logMap.put("url", request.getRequestURL().toString());
        UserEntity user = loginUtil.getCurrentUser(request);
        if(user != null){
            logMap.put("userId", user.getUserId());
        }
        logMap.put("query", request.getQueryString());
        //request中读取请求体需要采用readFromInputStream，无法reset，只能读一次
        //因此通过切面的方式，在joinPoint中读取请求参数
        MethodSignature methodSignature =(MethodSignature)joinPoint.getSignature();
        logMap.put("requestBody",ArgsUtil.paserString(methodSignature.getMethod(),joinPoint.getArgs()));

        return result;
    }
}

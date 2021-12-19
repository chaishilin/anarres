package com.csl.anarres.aspect;

import com.csl.anarres.annotation.RemoveCache;
import com.csl.anarres.utils.JoinPointUtil;
import com.csl.anarres.utils.LoginUtil;
import com.csl.anarres.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/24 10:07
 * @Description:
 */
@Aspect
@Component
public class RemoveCacheAspect {
    private Logger logger = LoggerFactory.getLogger(RemoveCacheAspect.class);

    @Autowired
    private LoginUtil loginUtil;

    //用来修饰controller层函数
    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..)) && @annotation(com.csl.anarres.annotation.RemoveCache)")
    public void removeCache() {
    }//只是个函数签名，帮助记录的

    @Around("removeCache()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) {
        Object result = JoinPointUtil.doRequest(joinPoint);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RemoveCache annotation = signature.getMethod().getAnnotation(RemoveCache.class);
        String requestMethod = annotation.requestMethod();
        String redisType = annotation.redisType();
        if("".equals(requestMethod)){
            logger.warn("无缓存清除地址");
            return result;
        }
        try {
            String[] paths = requestMethod.split(";");
            String userId = loginUtil.getCurrentUserOrPublic().getUserId();
            for(String path : paths){
                String key = path.replace("{userId}",userId);
                RedisUtil.getInstance().del(key);
                logger.info("成功删除缓存"+userId+":"+path);
            }
            return result;
        }catch (Exception e){
            logger.warn("清除缓存失败，字段名:"+requestMethod+" 字段类型："+redisType+"错误信息："+e.getMessage());
            return result;
        }

    }
}

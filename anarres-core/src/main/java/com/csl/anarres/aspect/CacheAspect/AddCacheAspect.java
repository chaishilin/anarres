//package com.csl.anarres.aspect.CacheAspect;
//
//import com.csl.anarres.annotation.CacheAnnotation.AddCache;
//import com.csl.anarres.entity.UserEntity;
//import com.csl.anarres.utils.JoinPointUtil;
//import com.csl.anarres.utils.LoginUtil;
//import com.csl.anarres.utils.RedisUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.lang.reflect.Method;
//
///**
// * @author: Shilin Chai
// * @Date: 2021/11/24 10:07
// * @Description:
// */
////@Aspect
////@Component
//public class AddCacheAspect {
//    private Logger logger = LoggerFactory.getLogger(AddCacheAspect.class);
//    @Autowired
//    private LoginUtil loginUtil;
//    @Pointcut("execution(public * com.csl.anarres.service.impl.*.*(..)) && @annotation(com.csl.anarres.annotation.CacheAnnotation.AddCache)")
//    public void addCache() {
//    }//只是个函数签名，帮助记录的
//
//    @Around("addCache()")
//    public Object Interceptor(ProceedingJoinPoint joinPoint) {
//        Object result = JoinPointUtil.doRequest(joinPoint);
//        //切面套娃了，需要修改
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method =  signature.getMethod();
//        AddCache annotation = method.getAnnotation(AddCache.class);
//        String path = annotation.path();
//        UserEntity user = loginUtil.getCurrentUser();
//
//        if(user != null && RedisUtil.hgetFromPath(user.getUserId(),path) == null){
//            //如果用户已登录，且缓存为空,将运行结果记入缓存
//            logger.info("将结果存入缓存，路径："+path);
//            RedisUtil.hmsetToPath(user.getUserId(),path,result);
//        }
//        return result;
//    }
//
//}

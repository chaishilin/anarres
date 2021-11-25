//package com.csl.anarres.aspect.CacheAspect;
//
//import com.csl.anarres.annotation.CacheAnnotation.ReadCache;
//import com.csl.anarres.utils.RedisUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//
//import java.lang.reflect.Method;
//
///**
// * @author: Shilin Chai
// * @Date: 2021/11/23 15:45
// * @Description: 缓存切面的抽象类，父类实现储存读取，子类实现解析返回
// */
////@Component
//public abstract class CacheAspectTemplate {
//    /**
//     * 1、根据路径查看有没有这个对象
//     * 2、有的话，进行解析（父类负责读出来，子类负责解析，规定返回值），如何返回
//     * 3、没有的话继续process
//     */
//    Logger logger = LoggerFactory.getLogger(CacheAspectTemplate.class);
//    Jedis jedis = RedisUtil.getInstance();
//
//    @Pointcut("execution(public * com.csl.anarres.controller.*.*(..)) && @annotation(com.csl.anarres.annotation.CacheAnnotation.ReadCache)")
//    public final void saveCache() {
//    }//只是个函数签名，帮助记录的
//
//    @Around("saveCache()")
//    public final Object Interceptor(ProceedingJoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method =  signature.getMethod();
//        ReadCache annotation = method.getAnnotation(ReadCache.class);
//        String path = annotation.path();
//        return paserFromCache(path,joinPoint,method);
//    }
//
//    public abstract Object paserFromCache(String path, ProceedingJoinPoint joinPoint,Method method);
//}

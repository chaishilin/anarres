//package com.csl.anarres.aspect.CacheAspect;
//
//import com.csl.anarres.entity.UserEntity;
//import com.csl.anarres.utils.JoinPointUtil;
//import com.csl.anarres.utils.LoginUtil;
//import com.csl.anarres.utils.RedisUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.lang.reflect.Method;
//
///**
// * @author: Shilin Chai
// * @Date: 2021/11/23 16:25
// * @Description:
// */
////@Aspect
////@Component
//public class CacheAspectUserImpl extends CacheAspectTemplate {
//    @Autowired
//    private LoginUtil loginUtil;
//    @Override
//    public Object paserFromCache(String path, ProceedingJoinPoint joinPoint, Method method) {
//        System.out.println(method.getName());
//        if (method.getName().equals("getUserInfo")) {
//            UserEntity user = loginUtil.getCurrentUser();
//            Object cacheResult = null;
//            if(user != null){
//                cacheResult = RedisUtil.hgetFromPath(user.getUserId(), path);
//            }
//            if (cacheResult != null ) {
//                //如果有缓存，则在该子类中解析缓存中数据
//                return cacheResult;
//            } else {
//                //如果无缓存，则继续响应
//                return JoinPointUtil.doRequest(joinPoint);
//            }
//        } else {
//            //不属于自己的不进行处理
//            return JoinPointUtil.doRequest(joinPoint);
//        }
//    }
//}
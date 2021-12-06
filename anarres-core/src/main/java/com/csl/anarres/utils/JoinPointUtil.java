package com.csl.anarres.utils;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/18 15:07
 * @Description:
 */
public class JoinPointUtil {
    public static Object doRequestCacheInHset(ProceedingJoinPoint joinPoint, String hashtableName , String key, long exTime) {
        Object result = null;
        try {
            result = doRequest(joinPoint);
            if(checkResponce(result)){
                RedisUtil.getInstance().hset(hashtableName,key, JSONObject.toJSONString(result));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Object doRequestCacheInKey(ProceedingJoinPoint joinPoint, String key, long exTime) {
        Object result = null;
        try {
            result = doRequest(joinPoint);
            if(checkResponce(result)){
                RedisUtil.getInstance().setex(key,exTime,JSONObject.toJSONString(result));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Object doRequest(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private static boolean checkResponce(Object result){
        JSONObject resultObject = JSONObject.parseObject(JSONObject.toJSONString(result));
        return resultObject != null && resultObject.getString("code").equals("200");
    }
}

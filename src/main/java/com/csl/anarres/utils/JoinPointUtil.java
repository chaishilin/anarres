package com.csl.anarres.utils;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import redis.clients.jedis.Jedis;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/18 15:07
 * @Description:
 */
public class JoinPointUtil {
    private static Jedis jedis = RedisUtil.getInstance();
    public static Object doRequestWithArg(ProceedingJoinPoint joinPoint, String argMD5, long exTime) {
        Object result = null;
        try {
            result = doRequest(joinPoint);
            jedis.setex(argMD5, exTime, JSONObject.toJSONString(result));
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
}

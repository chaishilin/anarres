package com.csl.anarres.utils;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Date;

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

    public static Object doRequestWithFrenquencyLimit(ProceedingJoinPoint joinPoint, String methodUser, int requestFrequency) {
        Long llen = RedisUtil.getInstance().llen(methodUser);

        //如果llen的长度等于请求次数，看llen的第一个是不是超过了一秒钟，如果是，那就移除第一个，并且继续请求
        if(llen < requestFrequency){
            //如果1s内的请求数量小于限制数量，则可以继续请求
            RedisUtil.getInstance().lpush(methodUser,String.valueOf(new Date().getTime()));
            return JoinPointUtil.doRequest(joinPoint);
        }else{
            String listHead = RedisUtil.getInstance().lindex(methodUser,llen-1);
            if(listHead != null &&  new Date().getTime() - Long.parseLong(listHead) > 1000){
                RedisUtil.getInstance().rpop(methodUser);
                RedisUtil.getInstance().lpush(methodUser,String.valueOf(new Date().getTime()));
                return JoinPointUtil.doRequest(joinPoint);
            }else{
                //否则报错，避免频繁请求
                return null;
            }
        }
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

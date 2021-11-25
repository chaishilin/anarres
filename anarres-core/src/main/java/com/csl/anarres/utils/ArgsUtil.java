package com.csl.anarres.utils;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.annotation.PaserUserState;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/22 16:43
 * @Description: 学习mall项目，对请求参数进行解析包装
 */
public class ArgsUtil {
    public static Object paserObject(Method method,Object[] args){
        Parameter[] parameters =  method.getParameters();
        for(int i = 0;i < parameters.length;i++){
            if(parameters[i].getAnnotation(RequestBody.class)!=null){
                return args[i];
            }else if(parameters[i].getAnnotation(PaserUserState.class)!=null) {
                return args[i];
            }
        }
        return null;
    }
    public static String paserString(Method method,Object[] args){
        Object result = paserObject(method,args);
        if(result == null){
            return null;
        }else{
            return JSONObject.toJSON(result).toString();
        }
    }
    public static JSONObject paserJSONObject(Method method,Object[] args){
        Object result = paserObject(method,args);
        if(result == null){
            return null;
        }else{
            return JSONObject.parseObject(JSONObject.toJSON(result).toString());
        }
    }
}

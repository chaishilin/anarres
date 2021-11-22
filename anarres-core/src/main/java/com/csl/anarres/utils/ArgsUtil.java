package com.csl.anarres.utils;

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
    public static String paserString(Method method,Object[] args){
        Parameter[] parameters =  method.getParameters();
        for(int i = 0;i < parameters.length;i++){
           if(parameters[i].getAnnotation(RequestBody.class)!=null){
               return args[i].toString();
           }else if(parameters[i].getAnnotation(PaserUserState.class)!=null) {
               return args[i].toString();
           }
        }
        return null;
    }
}

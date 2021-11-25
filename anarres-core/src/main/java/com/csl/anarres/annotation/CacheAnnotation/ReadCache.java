package com.csl.anarres.annotation.CacheAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/23 15:39
 * @Description: 对于请求内容相同，但是返回内容可能不同的请求，（例如查看程序列表，请求内容相同，但是返回的列表可能不同）可以利用该注解缓存响应内容
 * 用于修饰controller层
 * 添加该注解后，如果有缓存信息，则读取缓存信息
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadCache {
    /*
      路径形如a.b.c.d的形式
      a为hashtable中的键值，
      b.c.d为键对应的JSONObject中的路径
     */
    String path();
}
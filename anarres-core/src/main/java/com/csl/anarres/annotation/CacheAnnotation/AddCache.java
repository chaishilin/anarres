package com.csl.anarres.annotation.CacheAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/24 10:04
 * @Description: 添加该注解后，method的返回值被计入缓存
 * 用于修饰service层
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddCache {
    /*
      路径形如a.b.c.d的形式
      a为hashtable中的键值，
      b.c.d为键对应的JSONObject中的路径
    */
    String path();
}

package com.csl.anarres.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/11 10:36
 * @Description: 实现接口幂等性的注解，对于频繁的相同请求（value秒内的），通过缓存直接返回请求结果
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@CacheEnable
public @interface IdempotenceRequest {
    //可重复请求时间：单位秒
    int requestTime() default 60;
    String requestMethod();

}

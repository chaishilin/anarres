package com.csl.anarres.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/11 10:36
 * @Description:
 */
@Target({ElementType.METHOD})//还是要用拦截器或者aop 用aop试试
@Retention(RetentionPolicy.RUNTIME)
public @interface IdempotenceRequest {
    //可重复请求时间：单位毫秒
    int value() default 2000;
}

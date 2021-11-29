package com.csl.anarres.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/18 14:29
 * @Description: 防止频繁请求的注解，对于两个不同内容的频繁请求，限制请求间隔为value秒
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestFrequency {
    //两次不同请求的间隔时间：单位秒
    int value() default 2;
}

package com.csl.anarres.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 17:31
 * @Description:
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 用于解析生成programDTO类，
 * 对比createrId和token是否一致，并填入是否是用户本人、是否已登录字段，供controller程序使用
 */
public @interface PaserUserState {
}

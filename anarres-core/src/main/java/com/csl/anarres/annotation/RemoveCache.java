package com.csl.anarres.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/24 10:05
 * @Description: 在调用该方法后，清除path中的缓存信息，防止缓存和数据库信息不一致
 * 用于修饰service层
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoveCache {
    /*
      可以清除多个redis中的路径,分号分隔,example:programList;userList
    */
    String requestMethod() ;
}

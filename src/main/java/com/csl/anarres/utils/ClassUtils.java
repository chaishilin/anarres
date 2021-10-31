package com.csl.anarres.utils;

import com.csl.anarres.entity.ProgramEntity;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 21:11
 * @Description:
 */
public class ClassUtils {
    public static void changeClassContent(ProgramEntity entity, String filename){
        String content = entity.getCode();
        String[] results = content.split("Main");//其实还是用正则匹配比较好
        StringBuffer newContent = new StringBuffer();
        newContent.append(results[0]);
        newContent.append(filename);
        newContent.append(results[1]);
        entity.setCode(newContent.toString());
        return;
    }
    public static void genarateClassName(ProgramEntity entity){
        entity.setClassName(entity.getLanguage() + new Date().getTime());
    }
}

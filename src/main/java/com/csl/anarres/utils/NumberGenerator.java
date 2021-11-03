package com.csl.anarres.utils;

import com.csl.anarres.enums.TableIdEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/3 9:28
 * @Description:
 */
public class NumberGenerator {
    public static String getIdFromTableId(TableIdEnum tableId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tableId.getCode());
        stringBuilder.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        stringBuilder.append(String.format("%05d",RedisUtil.getInstance().incr(stringBuilder.toString())));
        return stringBuilder.toString();
    }
}

package com.csl.anarres.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 15:41
 * @Description:
 */
public class hashcodeBuilder {
    private hashcodeBuilder(){}
    public static String getHashcode(String str){
        return getHashcode(str,"MD5");
    }
    public static String getHashcode(String str,String method){
        try {
            MessageDigest md = MessageDigest.getInstance(method);
            md.update(str.getBytes());
            return new BigInteger(1,md.digest()).toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

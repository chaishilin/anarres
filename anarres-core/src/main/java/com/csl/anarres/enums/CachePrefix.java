package com.csl.anarres.enums;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/23 16:14
 * @Description:
 */
public enum CachePrefix {
    USER("user",1);
    private String prefix;
    private int code;

    CachePrefix(String prefix, int code) {
        this.prefix = prefix;
        this.code = code;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getCode() {
        return code;
    }
}

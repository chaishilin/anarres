package com.csl.anarres.enums;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/2 9:38
 * @Description:
 */
public enum OsType {
    Windows("windows",1),Linux("Linux",2),MacOs("macOS",3);
    private String osName;
    private int code;

    OsType(String osName, int code) {
        this.osName = osName;
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

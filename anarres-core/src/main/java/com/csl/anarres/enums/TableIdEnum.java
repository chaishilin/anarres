package com.csl.anarres.enums;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/3 9:35
 * @Description:
 */

public enum TableIdEnum {
    USER("user","01"),
    PROGRAM("program","02"),
    PROGRAMCODE("programCode","03"),
    LABEl("label","04"),
    NUMGENERATOR("numgenerator","05"),
    PROGRAMTEMPLATE("programTemplate","06"),
    DATATYPE("dateType","07")
    ;


    private String name;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    private String code;

    TableIdEnum(String name,String code){
        this.code = code;
        this.name = name;
    }

}

package com.csl.anarres.enums;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:40
 * @Description:
 */
public enum SupportLanguage {
    java("java",1);
    private String name;
    private int code;
    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }


    private SupportLanguage(String name,int code){
        this.code = code;
        this.name = name;
    }

    public static boolean isInclude(String name){
        for(SupportLanguage language:SupportLanguage.values()){
            if(language.name.equals(name)){
                return  true;
            }
        }
        return false;
    }
}

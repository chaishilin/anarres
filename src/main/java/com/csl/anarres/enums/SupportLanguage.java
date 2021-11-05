package com.csl.anarres.enums;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:40
 * @Description:
 */
public enum SupportLanguage {
    java("java",".java",1),
    python("python",".py",2);
    private String name;
    private String suffix;
    private int code;
    public String getName() {
        return name;
    }
    public String getSuffix() {
        return suffix;
    }

    public int getCode() {
        return code;
    }


    private SupportLanguage(String name,String suffix,int code){
        this.code = code;
        this.suffix = suffix;
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

package com.csl.anarres.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:07
 * @Description:
 */
@Data
public class ProgramDto {
    private String result;
    private String createrName;


    private String programId;
    private String language;
    private String code;
    private String createrId;
    private Date createTime;
    private Date lastModifiedTime;
    private String codeMD5;
    private String contentMD5;
    private String title;
    private String content;
    private String state;
    private String publicState;
    private String className;
    private String input;//程序的输入(main函数中的String[] args)
    private String output;//程序的输出
    private boolean needSave;
    private boolean readable;
}

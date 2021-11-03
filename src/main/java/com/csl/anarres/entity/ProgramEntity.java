package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:58
 * @Description:
 */
@Data
@TableName("program")
public class ProgramEntity {
    @TableId(value = "P_ID",type = IdType.INPUT)
    private String programId;
    @TableField("LANGUAGE")
    private String language;
    @TableField("CODE")
    private String code;
    @TableField("CREATER_ID")
    private String createrId;
    @TableField("CREATE_TIME")
    private Date createtime;
    @TableField("LAST_MODIFIED_TIME")
    private Date lastModifiedTime;
    @TableField("CODE_MD5")
    private String codeMD5;
    @TableField("CONTENT_MD5")
    private String contentMD5;
    @TableField("TITLE")
    private String title;
    @TableField("CONTENT")
    private String content;
    @TableField("STATE")
    private String state;
    @TableField("PUBLIC_STATE")
    private String publicState;
    @TableField(exist = false)
    private String className;
    @TableField(exist = false)
    private String input;//程序的输入(main函数中的String[] args)
    @TableField(exist = false)
    private String output;//程序的输出
    @TableField(exist = false)
    private boolean needSave;
    @TableField(exist = false)
    private boolean readable;

}

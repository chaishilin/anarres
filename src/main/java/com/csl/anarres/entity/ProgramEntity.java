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
    @TableId(value = "PROGRAM_ID",type = IdType.AUTO)
    private int programId;
    @TableField("LANGUAGE")
    private String language;
    @TableField("CODE")
    private String code;
    @TableField("CREATER_ID")
    private int createrId;
    @TableField("CREATETIME")
    private Date createtime;
    @TableField("CODEMD5")
    private String codeMD5;
    @TableField(exist = false)
    private String className;
    @TableField(exist = false)
    private String input;//程序的输入(main函数中的String[] args)
    @TableField(exist = false)
    private String output;//程序的输出
    @TableField(exist = false)
    private boolean needSave;

}

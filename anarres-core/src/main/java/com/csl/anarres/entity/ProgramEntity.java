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
public class ProgramEntity implements Comparable<ProgramEntity>,ProgramInterface{
    //程序id
    @TableId(value = "P_ID",type = IdType.INPUT)
    private String programId;
    //程序语言
    @TableField("LANGUAGE")
    private String language;
    //程序具体的代码内容
    @TableField("CODE")
    private String code;
    //创造者Id
    @TableField("CREATER_ID")
    private String createrId;
    //创建时间
    @TableField("CREATE_TIME")
    private Date createTime;
    //最后修改时间
    @TableField("LAST_MODIFIED_TIME")
    private Date lastModifiedTime;
    @TableField("CODE_MD5")
    private String codeMD5;
    @TableField("CONTENT_MD5")
    private String contentMD5;
    //文章标题
    @TableField("TITLE")
    private String title;
    //文章内容
    @TableField("CONTENT")
    private String content;
    //文章状态 01：正常，00：待删除
    @TableField("STATE")
    private String state;
    //公开状态：01：公开，00：不公开
    @TableField("PUBLIC_STATE")
    private String publicState;
    @TableField(exist = false)//数据库表中不存在该项
    private String className;
    @TableField(exist = false)
    private String input;//程序的输入(main函数中的String[] args)
    @TableField(exist = false)
    private String output;//程序的输出
    @TableField(exist = false)
    private boolean needSave;
    @TableField(exist = false)
    private boolean readable;
    //运行的程序是否报错
    @TableField(exist = false)
    private boolean isError;

    @Override
    public int compareTo(ProgramEntity entity){
        if(!this.getPublicState().equals(entity.getPublicState())){
            if("01".equals(this.getPublicState())){
                //公开的放前面
                return 1;
            }else{
                return -1;
            }
        }else {
            //同种对外状态，根据时间排序
            return this.getLastModifiedTime().compareTo(entity.getLastModifiedTime());
        }
    }
}

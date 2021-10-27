package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserEntity {
    @TableId(value = "ID",type = IdType.INPUT)
    private int id;
    @TableField("NAME")
    private String name;
    @TableField("PASSWORD")
    private String password;
    @TableField("CREATETIME")
    private Date createTime;
    @TableField("LASTMODIFIEDTIME")
    private Date lastModifiedTime;
}

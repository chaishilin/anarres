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
    @TableId(value = "U_ID",type = IdType.INPUT)
    private String userId;
    @TableField("U_NAME")
    private String username;
    @TableField("U_PASSWORD")
    private String password;
    @TableField("CREATE_TIME")
    private Date createTime;
    @TableField("LAST_MODIFIED_TIME")
    private Date lastModifiedTime;
    @TableField("U_PHONE")
    private String userPhone;
    @TableField("U_EMAIL")
    private String userEmail;
    @TableField("U_STATE")
    private String userState;
}

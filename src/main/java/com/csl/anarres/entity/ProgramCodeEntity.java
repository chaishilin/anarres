package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/06 18:58
 * @Description:
 */
@Data
@TableName("programCode")
public class ProgramCodeEntity {
    @TableId(value = "P_C_ID",type = IdType.INPUT)
    private String programCodeId;
    @TableField(value = "P_ID")
    private String programId;
    @TableField("LANGUAGE")
    private String language;
    @TableField("CODE")
    private String code;
    @TableField("CREATER_ID")
    private String createrId;
    @TableField("STATE")
    private String state;
    @TableField("CREATE_TIME")
    private Date createTime;
}

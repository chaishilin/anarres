package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/3 8:53
 * @Description:
 */
public class LableEntity {
    @TableId(value = "L_ID",type = IdType.INPUT)
    private String labelId;
    @TableField("U_ID")
    private String userId;
    @TableField("P_ID")
    private String programId;
    @TableField("LABEL_NAME")
    private String labelName;
}

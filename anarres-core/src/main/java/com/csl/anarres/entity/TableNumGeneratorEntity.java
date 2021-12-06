package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/6 14:05
 * @Description:
 */
@Data
@TableName("tableNumGenerator")
public class TableNumGeneratorEntity {
    @TableField("TABLE_NAME")
    private String tableName;
    @TableId(value = "TABLE_CODE",type = IdType.INPUT)
    private String code;
    @TableField("COUNT")
    private int count;
    @TableField("CREATE_TIME")
    private Date createTime;
    @TableField("LAST_MODIFIED_TIME")
    private Date lastModifiedTime;
}

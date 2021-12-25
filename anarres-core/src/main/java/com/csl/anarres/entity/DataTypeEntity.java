package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 14:20
 * @Description:
 */

/**
 * 有极速测试模式和普通测试模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dataType")
public class DataTypeEntity extends RecordEntity{
    //程序id
    @TableId(value = "D_ID",type = IdType.INPUT)
    private String dataTypeId;
    //程序语言
    @TableField("LANGUAGE")
    private String language;
    //数据类型定义
    @TableField("DEFINITION")
    private String definition;
    //测试用例输入示例
    @TableField("EXAMPLE")
    private String example;
    //标题
    @TableField("TITLE")
    private String title;
    //备注
    @TableField("CONTENT")
    private String content;
    //测试用例状态 01：正常，00：待删除，02：未启用
    @TableField("STATE")
    private String state;

}

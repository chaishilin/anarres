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
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("testCase")
public class TestCaseEntity extends RecordEntity{
    //程序id
    @TableId(value = "C_ID",type = IdType.INPUT)
    private String caseId;
    //程序语言
    @TableField("LANGUAGE")
    private String language;
    //测试程序具体的代码内容
    @TableField("CODE")
    private String code;
    //测试用例输入
    @TableField("INPUT")
    private String input;
    //测试用例输出
    @TableField("OUTPUT")
    private String output;
    //标题
    @TableField("TITLE")
    private String title;
    //内容
    @TableField("CONTENT")
    private String content;
    //测试用例状态 01：正常，00：待删除，02：未启用
    @TableField("STATE")
    private String state;
    //类名
    @TableField(exist = false)//数据库表中不存在该项
    private String className;

}

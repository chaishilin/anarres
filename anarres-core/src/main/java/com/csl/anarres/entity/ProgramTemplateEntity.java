package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/13 9:12
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("programTemplate")
public class ProgramTemplateEntity extends RecordEntity{
    //todo 自助注册支持的语言是否可行？
    //todo 各个支持语言的代码模板可以在线查看编辑
    //主键
    @TableId(value = "T_ID",type = IdType.INPUT)
    private String templateId;
    //代码模板
    @TableField("TEMPLATE")
    private String template;
    //代码模板语言
    @TableField("LANGUAGE")
    private String language;
    //当前代码是否公开
    @TableField("PUBLIC_STATE")
    private String publicState;
    //当前代码模板题目
    @TableField("TITLE")
    private String title;
    //当前代码模板内容描述
    @TableField("CONTENT")
    private String content;
    //当前模板使用状态
    @TableField("STATE")
    private String state;
}

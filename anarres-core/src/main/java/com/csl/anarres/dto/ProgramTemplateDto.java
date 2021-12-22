package com.csl.anarres.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 9:12
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProgramTemplateDto extends RecordDto {
    //主键
    private String templateId;
    //代码模板
    private String template;
    //代码模板语言
    private String language;
    //当前代码是否公开
    private String publicState;
    //当前代码模板题目
    private String title;
    //当前代码模板内容描述
    private String content;
    //当前模板使用状态
    private String state;
}

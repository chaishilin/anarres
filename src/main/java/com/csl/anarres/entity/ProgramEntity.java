package com.csl.anarres.entity;

import lombok.Data;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:58
 * @Description:
 */
@Data
public class ProgramEntity {
    String language;
    String code;
    String className;
    String output;//程序的输出
}

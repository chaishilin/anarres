package com.csl.anarres.dto;

import lombok.Data;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:13
 * @Description: 运行命令行程序所需要的信息的实体类
 */
@Data
public class ProgramRunnerDto {
    //执行命令所需要进入的路径
    private String path;
    //所运行的代码文件
    private String fileName;
    //所运行的代码文件生成的类文件名
    private String className;
    //所运行的代码文件的命令行参数
    private String input;
}

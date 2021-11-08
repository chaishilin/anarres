package com.csl.anarres.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:07
 * @Description:
 */
@Data
public class ProgramDto implements Comparable<ProgramDto> {
    private String result;
    private String createrName;
    private Map<String, String> codeMap;


    private String programId;
    private String language;
    private String code;
    private String createrId;
    private Date createTime;
    private Date lastModifiedTime;
    private String codeMD5;
    private String contentMD5;
    private String title;
    private String content;
    private String state;
    private String publicState;
    private String className;
    private String input;//程序的输入(main函数中的String[] args)
    private String output;//程序的输出
    private boolean needSave;
    private boolean readable;

    @Override
    public int compareTo(ProgramDto dto) {
        if (!this.getPublicState().equals(dto.getPublicState())) {
            if ("01".equals(this.getPublicState())) {
                //公开的放前面
                return 1;
            } else {
                return -1;
            }
        } else {
            //同种对外状态，根据时间排序
            return this.getLastModifiedTime().compareTo(dto.getLastModifiedTime());
        }

    }


}

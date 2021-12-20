package com.csl.anarres.dto;

import com.csl.anarres.entity.ProgramInterface;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 15:07
 * @Description:
 */
@Data
public class ProgramDto implements Comparable<ProgramDto>, ProgramInterface {
    /**
     * 运行结果
     */
    private String result;
    /**
     * 创建人名称
     */
    private String createrName;
    /**
     * 代码map{语言->代码}
     */
    private Map<String, String> codeMap;
    /**
     * 是否本人
     */
    private boolean isSelf;
    /**
     * 是否登录
     */
    private boolean isLogin;

    /**
     * 程序id
     */
    private String programId;
    private String language;//待删除
    private String code;//待删除
    private String createrId;
    private Date createTime;
    private Date lastModifiedTime;
    private String codeMD5;//待删除
    private String contentMD5;//待删除
    private String title;
    private String content;
    private String state;
    private String publicState;
    private String className;
    private String input;//程序的输入(main函数中的String[] args)
    private String output;//程序的输出
    private boolean needSave;
    private boolean readable;
    /**
     * 运行代码所使用的模板id
     */
    private String templateId;
    //运行的程序是否报错
    private boolean isError;

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

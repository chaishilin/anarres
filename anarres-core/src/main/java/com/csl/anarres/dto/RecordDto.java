package com.csl.anarres.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 10:22
 * @Description:
 */
@Data
public class RecordDto {
    //用户id
    private String userId;
    //创造者Id
    private String createrId;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastModifiedTime;
}

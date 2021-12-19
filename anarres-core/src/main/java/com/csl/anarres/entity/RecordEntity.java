package com.csl.anarres.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/13 9:14
 * @Description:
 */
@Data
public class RecordEntity {
    //创造者Id
    @TableField("CREATER_ID")
    private String createrId;
    //创建时间
    @TableField("CREATE_TIME")
    private Date createTime;
    //最后修改时间
    @TableField("LAST_MODIFIED_TIME")
    private Date lastModifiedTime;
}

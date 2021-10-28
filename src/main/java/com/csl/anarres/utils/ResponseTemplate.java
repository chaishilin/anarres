package com.csl.anarres.utils;

import lombok.Data;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 11:35
 * @Description:
 */
@Data
public class ResponseTemplate {
    private int code;
    private String msg;
    private Object data;

}

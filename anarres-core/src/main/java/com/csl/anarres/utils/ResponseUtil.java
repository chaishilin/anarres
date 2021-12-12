package com.csl.anarres.utils;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 11:37
 * @Description:
 */
public class ResponseUtil {

    public static ResponseTemplate success(String message, Object data){
        ResponseTemplate msg= new ResponseTemplate();
        msg.setCode(200);
        msg.setData(data);
        msg.setMsg(message);
        return msg;
    }

    public static ResponseTemplate success(Object data){
        return success("",data);
    }

    public static ResponseTemplate fail(Object data){
        return fail("",data);
    }

    public static ResponseTemplate fail(String message, Object data){
        ResponseTemplate msg = new ResponseTemplate();
        msg.setCode(500);
        msg.setData(data);
        msg.setMsg(message);
        return msg;
    }

    public static ResponseTemplate fail(String message){
        ResponseTemplate msg = new ResponseTemplate();
        msg.setCode(500);
        msg.setMsg(message);
        return msg;
    }

    public static ResponseTemplate fail(int code,String message){
        ResponseTemplate msg = new ResponseTemplate();
        msg.setCode(code);
        msg.setMsg(message);
        return msg;
    }
}

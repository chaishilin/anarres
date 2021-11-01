package com.csl.anarres.service;

import com.csl.anarres.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/1 17:24
 * @Description:
 */
public interface LoginService {
    UserEntity getUserInfo(HttpServletRequest request);
}

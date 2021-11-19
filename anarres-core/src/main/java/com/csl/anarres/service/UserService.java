package com.csl.anarres.service;

import com.csl.anarres.entity.UserEntity;

import java.util.List;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    UserEntity register(UserEntity user);

    /**
     * 登录
     * @param user
     * @return
     */
    UserEntity login(UserEntity user);

    /**
     * 根据用户信息生成token
     * @param user
     * @return
     */
    String generateToken(UserEntity user);
    List<UserEntity> userInfoList();

    /**
     * 忘记密码后重设密码
     * @param user
     * @return
     */
    UserEntity resetPassword(UserEntity user);
}

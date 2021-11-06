package com.csl.anarres.service.impl;

import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.mapper.UserMapper;
import com.csl.anarres.service.LoginService;
import com.csl.anarres.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/1 17:24
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserEntity getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        Jedis jedis = RedisUtil.getInstance();
        String userId = jedis.get(token);
        return userMapper.selectById(userId);
    }
}

package com.csl.anarres.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.mapper.UserMapper;
import com.csl.anarres.service.LoginService;
import com.csl.anarres.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserEntity getUserInfo(HttpServletRequest request) {
        //结果存入缓存
        //这个方法比较简单，并且在aop中也有调用，所以运用缓存注解来添加注解会套娃
        //因此手动存入缓存
        String userId = getUserId(request);
        if(userId == null){
            return null;
        }else{
            Jedis jedis = RedisUtil.getInstance();
            String userJson = jedis.get("userInfo"+userId);
            try {
                //如果缓存中有，则直接返回
                if(userJson == null || "".equals(userJson)){
                    throw new RuntimeException("empty");
                }
                logger.info("从缓存中直接返回userInfo");
                return JSONObject.parseObject(userJson,UserEntity.class);
            }catch (Exception e){
                //如果缓存中没有，则计入缓存
                UserEntity result = userMapper.selectById(userId);
                jedis.set("userInfo"+userId, JSONObject.toJSONString(result));
                return result;
            }
        }
    }

    @Override
    public String getUserId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token == null || token.equals("")){
            return null;
        }else{
            Jedis jedis = RedisUtil.getInstance();
            return jedis.get(token);
        }
    }
}

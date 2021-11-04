package com.csl.anarres.controller;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.UserService;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfoList")
    public ResponseTemplate userInfoList(){
        try {
            List<UserEntity> userEntityList = userService.userInfoList();
            return ResponseUtil.success("查询成功",userEntityList);
        }catch (Exception e){
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseTemplate login(@RequestBody UserEntity user){
        try {
            JSONObject result = new JSONObject();
            user = userService.login(user);
            String token = userService.generateToken(user);
            Jedis jedis = RedisUtil.getInstance();
            jedis.setex(token,(long)60*60,""+user.getUserId());
            result.put("token",token);
            result.put("userId",user.getUserId());
            return ResponseUtil.success("登陆成功",result);
        }catch (Exception e){
            return ResponseUtil.fail(e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseTemplate register(@RequestBody UserEntity user){
        try {
            userService.register(user);
            return ResponseUtil.success("注册成功");
        }catch (Exception e){
            return ResponseUtil.fail(e.getMessage());
        }
    }
}
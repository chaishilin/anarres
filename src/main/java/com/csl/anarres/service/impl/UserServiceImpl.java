package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.mapper.UserMapper;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity register(UserEntity user){
        QueryWrapper<UserEntity> qw = new QueryWrapper<>();
        qw.eq("NAME",user.getName());
        List<UserEntity> userEntityList = userMapper.selectList(qw);
        if(userEntityList.size() > 0){
            throw new RuntimeException("用户名重复！");
        }
        user.setCreateTime(new Date());
        user.setLastModifiedTime(new Date());
        userMapper.insert(user);
        userEntityList = userMapper.selectList(qw);
        assert userEntityList.size() == 1;
        return userEntityList.get(0);

    }
    @Override
    public UserEntity login(UserEntity user){
        QueryWrapper<UserEntity> qw = new QueryWrapper<>();
        qw.eq("NAME",user.getName());
        List<UserEntity> userEntityList = userMapper.selectList(qw);
        if(userEntityList.size() == 0){
            throw new RuntimeException("用户名错误！");
        }
        qw.eq("PASSWORD",user.getPassword());
        userEntityList = userMapper.selectList(qw);
        if(userEntityList.size() == 0){
            throw new RuntimeException("密码错误！");
        }
        assert userEntityList.size() == 1;
        return userEntityList.get(0);
    }
    @Override
    public List<UserEntity> find(){
        return userMapper.find();
    };
}

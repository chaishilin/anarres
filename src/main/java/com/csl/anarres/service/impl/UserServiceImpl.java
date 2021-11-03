package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.UserMapper;
import com.csl.anarres.service.UserService;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity register(UserEntity user){
        user.setPassword(HashcodeBuilder.getHashcode(user.getPassword()));
        QueryWrapper<UserEntity> qw = new QueryWrapper<>();
        qw.eq("U_NAME",user.getUsername());
        List<UserEntity> userEntityList = userMapper.selectList(qw);
        if(userEntityList.size() > 0){
            throw new RuntimeException("用户名重复！");
        }
        user.setCreateTime(new Date());
        user.setLastModifiedTime(new Date());
        if(user.getUserState() == null || "".equals(user.getUserState())){
            user.setUserState("01");
        }
        user.setUserId(NumberGenerator.getIdFromTableId(TableIdEnum.USER));
        userMapper.insert(user);
        userEntityList = userMapper.selectList(qw);
        assert userEntityList.size() == 1;
        return userEntityList.get(0);

    }

    @Override
    public UserEntity login(UserEntity user){
        user.setPassword(HashcodeBuilder.getHashcode(user.getPassword()));
        QueryWrapper<UserEntity> qw = new QueryWrapper<>();
        qw.eq("U_NAME",user.getUsername());
        List<UserEntity> userEntityList = userMapper.selectList(qw);
        if(userEntityList.size() == 0){
            throw new RuntimeException("用户名错误！");
        }
        qw.eq("U_PASSWORD",user.getPassword());
        userEntityList = userMapper.selectList(qw);
        if(userEntityList.size() == 0){
            throw new RuntimeException("密码错误！");
        }
        assert userEntityList.size() == 1;
        return userEntityList.get(0);
    }
    @Override
    public String generateToken(UserEntity user){
        StringBuilder token = new StringBuilder();
        token.append("anarres");
        token.append(user.getUsername());
        token.append(new Date());
        Random random = new Random();
        token.append(random.nextInt(10000));
        return HashcodeBuilder.getHashcode(token.toString());
    }

    @Override
    public List<UserEntity> userInfoList(){
        List<UserEntity> a = userMapper.userInfoList();
        return a;

        //return userMapper.userInfoList();
    };
}

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
    @Autowired
    private NumberGenerator numberGenerator;
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
        user.setUserId(numberGenerator.getIdFromTableId(TableIdEnum.USER));
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

    @Override
    public UserEntity resetPassword(UserEntity entity){
        //用户名和邮箱对就行
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("U_NAME",entity.getUsername());
        queryWrapper.eq("U_EMAIL",entity.getUserEmail());
        List<UserEntity> userEntityList = userMapper.selectList(queryWrapper);
        if(userEntityList.size() == 0){
            throw new RuntimeException("无该用户或用户名与邮箱不匹配");
        }else if(userEntityList.size() > 1){
            throw  new RuntimeException("用户重复");
        }else{
            UserEntity newEntity = userEntityList.get(0);
            newEntity.setPassword(HashcodeBuilder.getHashcode(entity.getPassword()));
            userMapper.updateById(newEntity);
            return newEntity;
        }

    }
}

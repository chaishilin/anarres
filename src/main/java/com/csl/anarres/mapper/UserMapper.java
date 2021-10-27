package com.csl.anarres.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csl.anarres.entity.UserEntity;

import java.util.List;

public interface UserMapper extends BaseMapper<UserEntity> {
    List<UserEntity> find();
}

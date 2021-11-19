package com.csl.anarres.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csl.anarres.entity.ProgramCodeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/6 19:12
 * @Description:
 */
public interface ProgramCodeMapper extends BaseMapper<ProgramCodeEntity> {

    int softDeleteList(@Param("programId") String programId);

    int insertList(List<ProgramCodeEntity> entities);
}

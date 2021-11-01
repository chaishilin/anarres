package com.csl.anarres.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csl.anarres.entity.ProgramEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/1 17:12
 * @Description:
 */
public interface ProgramMapper extends BaseMapper<ProgramEntity> {
    List<ProgramEntity> findProgram(@Param("query") ProgramEntity entity);
}

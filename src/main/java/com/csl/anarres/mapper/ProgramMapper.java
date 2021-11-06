package com.csl.anarres.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/1 17:12
 * @Description:
 */
public interface ProgramMapper extends BaseMapper<ProgramEntity> {
    List<ProgramDto> findProgramList(@Param("query") ProgramEntity entity);//todo 目前只能根据个人id查自己的程序，以后做成分权限管理的，起码做成公共的和私人的
    List<ProgramDto> findEmptyProgramList(@Param("query") ProgramEntity entity);//todo 目前只能根据个人id查自己的程序，以后做成分权限管理的，起码做成公共的和私人的

}

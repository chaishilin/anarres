package com.csl.anarres.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csl.anarres.entity.ProgramTemplateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/13 11:41
 * @Description:
 */
public interface ProgramTemplateMapper extends BaseMapper<ProgramTemplateEntity> {
    List<ProgramTemplateEntity> findProgramTemplateList(@Param("query") ProgramTemplateEntity entity);
}

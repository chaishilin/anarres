package com.csl.anarres.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csl.anarres.entity.DataTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 14:32
 * @Description:
 */
public interface DataTypeMapper extends BaseMapper<DataTypeEntity> {
    List<DataTypeEntity> findDataTypeList(@Param("query") DataTypeEntity entity);
}

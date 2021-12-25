package com.csl.anarres.service;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.DataTypeEntity;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 14:30
 * @Description:
 */
public interface DataTypeService {
    /**
     * 查找
     * @param entity
     * @return
     */
    List<DataTypeEntity> select(DataTypeEntity entity);

    /**
     * 修改
     * @param entity
     * @return id
     */
    String save(DataTypeEntity entity);

    /**
     * 删除
     * @param entity
     * @return
     */
    String softDelete(DataTypeEntity entity);

    /**
     * 运行测试
     * @param entity
     * @return
     */
    ProgramDto run(DataTypeEntity entity);
}

package com.csl.anarres.service;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.DateTypeEntity;

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
    List<DateTypeEntity> select(DateTypeEntity entity);

    /**
     * 修改
     * @param entity
     * @return id
     */
    String save(DateTypeEntity entity);

    /**
     * 删除
     * @param entity
     * @return
     */
    String softDelete(DateTypeEntity entity);

    /**
     * 运行测试
     * @param entity
     * @return
     */
    ProgramDto run(DateTypeEntity entity);
}

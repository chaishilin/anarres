package com.csl.anarres.service;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.TestCaseEntity;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 14:30
 * @Description:
 */
public interface TestCaseService {
    /**
     * 查找
     * @param entity
     * @return
     */
    List<TestCaseEntity> select(TestCaseEntity entity);

    /**
     * 修改
     * @param entity
     * @return id
     */
    String save(TestCaseEntity entity);

    /**
     * 删除
     * @param entity
     * @return
     */
    String softDelete(TestCaseEntity entity);

    /**
     * 运行测试
     * @param entity
     * @return
     */
    ProgramDto run(TestCaseEntity entity);
}

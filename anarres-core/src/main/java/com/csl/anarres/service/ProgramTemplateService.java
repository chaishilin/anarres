package com.csl.anarres.service;

import com.csl.anarres.entity.ProgramTemplateEntity;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/13 11:37
 * @Description:
 */
public interface ProgramTemplateService {
    /**
     * 查找
     * @param entity
     * @return
     */
    List<ProgramTemplateEntity> select(ProgramTemplateEntity entity);

    /**
     * 修改
     * @param entity
     * @return id
     */
    String save(ProgramTemplateEntity entity);

    /**
     * 删除
     * @param entity
     * @return
     */
    String softDelete(ProgramTemplateEntity entity);

    /**
     * 模板测试
     * @param entity
     * @return
     */
    String runTemplate(ProgramTemplateEntity entity);

}

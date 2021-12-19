package com.csl.anarres.service.impl;

import com.csl.anarres.entity.ProgramTemplateEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.ProgramTemplateMapper;
import com.csl.anarres.service.ProgramTemplateService;
import com.csl.anarres.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/13 11:38
 * @Description:
 */
@Service
public class ProgramTemplateServiceImpl implements ProgramTemplateService {
    @Autowired
    private ProgramTemplateMapper mapper;
    @Autowired
    private NumberGenerator numberGenerator;

    @Override
    public List<ProgramTemplateEntity> select(ProgramTemplateEntity entity) {
        entity.setState("01");
        return mapper.findProgramTemplateList(entity);
    }

    @Override
    public String save(ProgramTemplateEntity entity) {
        entity.setState("01");
        if(entity.getTemplateId() == null || "".equals(entity.getTemplateId())){
            //如果是新增
            String templateId = numberGenerator.getIdFromTableId(TableIdEnum.PROGRAMTEMPLATE);
            entity.setTemplateId(templateId);
            entity.setCreateTime(new Date());
            entity.setLastModifiedTime(new Date());
            mapper.insert(entity);
        }else{
            //如果是更新
            entity.setLastModifiedTime(new Date());
            mapper.updateById(entity);
        }
        return entity.getTemplateId();
    }

    @Override
    public String softDelete(ProgramTemplateEntity dto) {
        ProgramTemplateEntity entity = mapper.selectById(dto.getTemplateId());
        entity.setState("00");
        mapper.updateById(entity);
        return entity.getTemplateId();
    }
}

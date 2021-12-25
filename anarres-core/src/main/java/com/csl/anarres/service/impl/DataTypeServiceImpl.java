package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.DateTypeEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.DataTypeMapper;
import com.csl.anarres.service.DataTypeService;
import com.csl.anarres.utils.NumberGenerator;
import com.csl.anarres.utils.ProgramRunner.ProgramRunnerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/22 14:31
 * @Description:
 */
@Service
public class DataTypeServiceImpl implements DataTypeService {
    @Autowired
    private DataTypeMapper mapper;
    @Autowired
    private NumberGenerator numberGenerator;
    @Autowired
    private ProgramRunnerFactory programRunnerFactory;
    @Override
    public List<DateTypeEntity> select(DateTypeEntity entity) {
        QueryWrapper<DateTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("STATE","00");
        if(entity.getDataTypeId() != null && !"".equals(entity.getDataTypeId())){
            queryWrapper.eq("D_ID",entity.getDataTypeId());
        }
        return mapper.selectList(queryWrapper);
    }

    @Override
    public String save(DateTypeEntity entity) {
        String caseId = null;
        if(entity.getDataTypeId() == null || "".equals(entity.getDataTypeId())){
            //新增
            caseId = numberGenerator.getIdFromTableId(TableIdEnum.DATATYPE);
            entity.setDataTypeId(caseId);
            mapper.insert(entity);
        }else{
            //修改
            mapper.updateById(entity);
        }
        return caseId;
    }

    @Override
    public String softDelete(DateTypeEntity entity) {
        entity.setState("00");
        mapper.updateById(entity);
        return entity.getDataTypeId();
    }

    @Override
    public ProgramDto run(DateTypeEntity entity) {
        return new ProgramDto();
    }
}
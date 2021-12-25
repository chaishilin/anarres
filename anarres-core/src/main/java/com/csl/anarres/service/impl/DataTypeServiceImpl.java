package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.DataTypeEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.DataTypeMapper;
import com.csl.anarres.service.DataTypeService;
import com.csl.anarres.utils.NumberGenerator;
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

    @Override
    public List<DataTypeEntity> select(DataTypeEntity entity) {
        //todo mapper层重新写
        QueryWrapper<DataTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("STATE","00");
        queryWrapper.eq("LANGUAGE","java");
        if(entity.getDataTypeId() != null && !"".equals(entity.getDataTypeId())){
            queryWrapper.eq("D_ID",entity.getDataTypeId());
        }
        return mapper.selectList(queryWrapper);
    }

    @Override
    public String save(DataTypeEntity entity) {
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
    public String softDelete(DataTypeEntity entity) {
        entity.setState("00");
        mapper.updateById(entity);
        return entity.getDataTypeId();
    }

    @Override
    public ProgramDto run(DataTypeEntity entity) {
//        ProgramRunner runner = programRunnerFactory.getRunner(entity.getLanguage());
//        String code = runner.generateSimpleFunction(entity);
//        ProgramDto result = runner.runWithTemplate(code,entity.getExample());
//        return result;
        return new ProgramDto();
    }
}
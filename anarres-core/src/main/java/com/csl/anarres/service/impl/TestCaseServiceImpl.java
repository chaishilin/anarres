package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.TestCaseEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.TestCaseMapper;
import com.csl.anarres.service.TestCaseService;
import com.csl.anarres.utils.NumberGenerator;
import com.csl.anarres.utils.ProgramRunner.ProgramRunner;
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
public class TestCaseServiceImpl implements TestCaseService {
    @Autowired
    private TestCaseMapper mapper;
    @Autowired
    private NumberGenerator numberGenerator;
    @Autowired
    private ProgramRunnerFactory programRunnerFactory;
    @Override
    public List<TestCaseEntity> select(TestCaseEntity entity) {
        QueryWrapper<TestCaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("STATE","00");
        if(entity.getCaseId() != null && !"".equals(entity.getCaseId())){
            queryWrapper.eq("C_ID",entity.getCaseId());
        }
        return mapper.selectList(queryWrapper);
    }

    @Override
    public String save(TestCaseEntity entity) {
        String caseId = null;
        if(entity.getCaseId() == null || "".equals(entity.getCaseId())){
            //新增
            caseId = numberGenerator.getIdFromTableId(TableIdEnum.TESTCASE);
            entity.setCaseId(caseId);
            mapper.insert(entity);
        }else{
            //修改
            mapper.updateById(entity);
        }
        return caseId;
    }

    @Override
    public String softDelete(TestCaseEntity entity) {
        entity.setState("00");
        mapper.updateById(entity);
        return entity.getCaseId();
    }

    @Override
    public ProgramDto run(TestCaseEntity entity) {
        ProgramRunner runner = programRunnerFactory.getRunner(entity.getLanguage());
        ProgramDto result = runner.run(entity.getCode(),entity.getInput());
        return result;
    }
}

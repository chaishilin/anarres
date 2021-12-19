package com.csl.anarres.service.impl;

import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.entity.ProgramTemplateEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.ProgramMapper;
import com.csl.anarres.mapper.ProgramTemplateMapper;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.service.ProgramTemplateService;
import com.csl.anarres.utils.NumberGenerator;
import com.csl.anarres.utils.ProgramRunner.PythonRunner;
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
    @Autowired
    private PythonRunner pythonRunner;
    @Autowired
    private ProgramMapper programMapper;
    @Autowired
    private ProgramService programService;

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

    @Override
    public String runTemplate(ProgramTemplateEntity entity){
        String code = entity.getCode();
        ProgramEntity programEntity = programMapper.selectById("022021112400003");
        code = code.replace("{{FunctionBody}}",pythonRunner.getFunctionBody(programEntity));
        code = code.replace("{{FunctionName}}",pythonRunner.getFunctioName(programEntity));
        code = code.replace("{{Parameters}}",pythonRunner.getParameters(programEntity));
        System.out.println(code);
        //programService.doProgram();
        return code;
    }

}

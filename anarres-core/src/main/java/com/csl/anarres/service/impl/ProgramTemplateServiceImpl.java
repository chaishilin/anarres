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
        //todo 运行程序的时候，从模板数据库中挑选模板
        //todo 可以进行分离：程序模板和程序语言，因为同一个编程语言可能存在不同的模板。运行程序时需要选择程序语言和对应的模板。
        String code = entity.getCode();
        //将所有的程序进行测试时，用时很长，如何生成一点数据就返回一点，展示一点，慢慢展示？
        //todo 一种土方法就是前端重复请求n次，有点土
        ProgramEntity programEntity = programMapper.selectById("022021121900001");//暂时随便找了一段不需要输入参数的程序
        code = code.replace("{{FunctionBody}}",pythonRunner.getFunctionBody(programEntity));
        code = code.replace("{{FunctionName}}",pythonRunner.getFunctioName(programEntity));
        code = code.replace("{{Parameters}}",pythonRunner.getParameters(programEntity));
        String result = pythonRunner.run(code);
        //programService.doProgram();
        return result;
    }

}
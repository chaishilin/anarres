package com.csl.anarres.utils.Iterator.ProcessIterator.impl;

import com.csl.anarres.entity.DataTypeEntity;
import com.csl.anarres.mapper.DataTypeMapper;
import com.csl.anarres.utils.Iterator.ProcessIterator.ProcessIterator;
import com.csl.anarres.utils.ProgramRunner.ProgramRunner;
import com.csl.anarres.utils.ProgramRunner.ProgramRunnerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/25 17:35
 * @Description:
 */
@Component(value = "TemplateTestIterator")
public class TemplateTestIterator extends ProcessIterator {
    @Autowired
    private DataTypeMapper mapper;
    @Autowired
    private ProgramRunnerFactory programRunnerFactory;

//    @Override
//    public List<? extends Object> buildWorkingList() {
//        DataTypeEntity query = new DataTypeEntity();
//        query.setState("01");
//        //query.setLanguage("java");
//        QueryWrapper<DataTypeEntity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("LANGUAGE",this.entity.getLanguage());
//        //queryWrapper.eq("LANGUAGE","JAVA");
//        queryWrapper.eq("STATE","01");
//        List<DataTypeEntity> workinglist = mapper.selectList(queryWrapper);
//        return workinglist;
//    }

    @Override
    public void process(Object o) {

        DataTypeEntity entity = (DataTypeEntity)o;
        System.out.println("test 运行"  + entity.getDefinition());
        ProgramRunner runner = programRunnerFactory.getRunner(entity.getLanguage());
        String code = runner.generateSimpleFunction(entity);
        runner.runWithTemplate(code,entity.getExample());
    }
}

package com.csl.anarres.utils.IterableProcess.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.dto.ProgramTemplateDto;
import com.csl.anarres.entity.DataTypeEntity;
import com.csl.anarres.mapper.DataTypeMapper;
import com.csl.anarres.utils.IterableProcess.IterableProcess;
import com.csl.anarres.utils.Iterator.ProcessIterator.ProcessIteratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/25 21:43
 * @Description:
 */
@Component
public class TemplateTestProcess extends IterableProcess {

    @Autowired
    private DataTypeMapper dataTypeMapper;

    @Autowired
    private ProcessIteratorFactory iteratorFactory;

//    @Override
//    public Iterator iterator() {
//        //如何用工厂函数 自动注入  带参数构造器
//        return iteratorFactory.getProcessIterator(TemplateTestProcess.class.getSimpleName(),workingList);
//    }

    @Override
    public void generateWorkingList(Object params) {
        ProgramTemplateDto dto = (ProgramTemplateDto) params;
        DataTypeEntity query = new DataTypeEntity();
        query.setState("01");
        QueryWrapper<DataTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("LANGUAGE",dto.getLanguage());
        queryWrapper.eq("STATE","01");
        List<?> workingList = dataTypeMapper.selectList(queryWrapper);
        super.setWorkingList(workingList);
    }

    @Override
    public String getClassName(){
        return this.getClass().getSimpleName();
    }
}

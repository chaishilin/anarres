package com.csl.anarres;

import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.mapper.ProgramCodeMapper;
import com.csl.anarres.mapper.ProgramMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AnarresApplicationTests {
    @Autowired
    ProgramMapper programMapper;
    @Autowired
    ProgramCodeMapper programCodeMapper;
    @Test
    void contextLoads() {
        ProgramEntity entity = new ProgramEntity();
        entity.setCreaterId("012021110300007");
        List<ProgramDto> result =  programMapper.findProgramList(entity);
        for(ProgramDto dto:result){
            System.out.println(dto.getProgramId() + dto.getLanguage());
        }
        /*
         List<ProgramEntity>  a= programMapper.selectList(new QueryWrapper<>());
         a.forEach(p->{
             System.out.println(p.getProgramId());
             ProgramCodeEntity programCodeEntity = new ProgramCodeEntity();
             programCodeEntity.setProgramCodeId(NumberGenerator.getIdFromTableId(TableIdEnum.PROGRAMCODE));
             programCodeEntity.setCode(p.getCode());
             programCodeEntity.setCodeMD5(p.getCodeMD5());
             programCodeEntity.setCreateTime(p.getCreateTime());
             programCodeEntity.setLastModifiedTime(p.getLastModifiedTime());
             programCodeEntity.setCreaterId(p.getCreaterId());
             programCodeEntity.setLanguage(p.getLanguage());
             programCodeEntity.setProgramId(p.getProgramId());
             programCodeMapper.insert(programCodeEntity);
         });


         */

    }



}

package com.csl.anarres.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.entity.ProgramCodeEntity;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.ProgramCodeMapper;
import com.csl.anarres.mapper.ProgramMapper;
import com.csl.anarres.service.ProgramService;
import com.csl.anarres.utils.CMDUtils.CMDUtils;
import com.csl.anarres.utils.FileUtil;
import com.csl.anarres.utils.HashcodeBuilder;
import com.csl.anarres.utils.NumberGenerator;
import com.csl.anarres.utils.ProgramRunner.ProgramRunnerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 14:59
 * @Description:
 */
@Service
public class ProgramServiceImpl implements ProgramService {
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private ProgramMapper mapper;
    @Autowired
    private ProgramCodeMapper codeMapper;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private NumberGenerator numberGenerator;
    @Autowired
    private CMDUtils cmdUtils;
    @Autowired
    private ProgramRunnerFactory programRunnerFactory;
    @Override
    public List<ProgramDto> programList(ProgramEntity entity) {
        List<ProgramDto> programDtos = mapper.findProgramList(entity);//这个是查出来有具体程序的
        Map<String, List<ProgramDto>> programMap = programDtos.stream().collect(Collectors.groupingBy(ProgramDto::getProgramId));
        List<ProgramDto> result = new ArrayList<>();
        programMap.forEach((programId, dtos) -> {
            Map<String, String> codeMap = new HashMap<>();
            dtos.forEach(p -> {
                codeMap.put(p.getLanguage(), p.getCode());
            });
            ProgramDto item = dtos.get(0);
            item.setLanguage("");
            item.setCode("");
            item.setCodeMap(codeMap);
            result.add(item);
        });
        result.addAll(mapper.findEmptyProgramList(entity));
        result.sort(ProgramDto::compareTo);
        Collections.reverse(result);
        return result;
    }

    @Override
    public void doProgram(ProgramDto dto) {
        programRunnerFactory.getRunner(dto.getLanguage()).runWithTemplate(dto);
    }

    @Override
    public void deleteProgram(ProgramDto dto) {
        ProgramEntity entity = mapper.selectById(dto.getProgramId());
        entity.setState("00");
        mapper.updateById(entity);
    }

    public String saveProgramToSql(ProgramDto dto) {
        //todo 删除ProgramDto中不用的code和language字段，把codeMD5改成entityMD5
        ProgramEntity entity = new ProgramEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setCodeMD5(HashcodeBuilder.getHashcode(entity.getCode()));
        entity.setContentMD5(HashcodeBuilder.getHashcode(entity.getContent()));
        entity.setState("01");
        if(entity.getPublicState() == null || "".equals(entity.getPublicState())){
            entity.setPublicState("01");
        }
        entity.setLastModifiedTime(new Date());

        //对发送主键的进行更新，对无主键的进行新增
        if (entity.getProgramId() == null || "".equals(entity.getProgramId())) {
            //无主键
            entity.setProgramId(numberGenerator.getIdFromTableId(TableIdEnum.PROGRAM));
            entity.setCreateTime(new Date());
            mapper.insert(entity);
        } else {
            //有主键，更新
            mapper.updateById(entity);
        }
        List<ProgramCodeEntity> programCodeEntities = new ArrayList<>();

        dto.getCodeMap().forEach((key, value) -> {
            if (value != null && !"".equals(value.replace("\n",""))) {
                //如果该语言对应的值不为空
                //System.out.println(key);
                ProgramCodeEntity codeEntity = new ProgramCodeEntity();
                codeEntity.setProgramId(entity.getProgramId());
                codeEntity.setLanguage(key);
                codeEntity.setCreaterId(entity.getCreaterId());
                codeEntity.setProgramCodeId(numberGenerator.getIdFromTableId(TableIdEnum.PROGRAMCODE));
                codeEntity.setCode(value);
                codeEntity.setCreateTime(new Date());
                programCodeEntities.add(codeEntity);
            }
        });
        codeMapper.softDeleteList(dto.getProgramId());//插入前先软删除以前的
        if(programCodeEntities.size()>0){
            codeMapper.insertList(programCodeEntities);
        }
        return entity.getProgramId();
    }

    @Override
    public void hardDeleteProgram() {
        QueryWrapper<ProgramEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATE", "00");
        queryWrapper.lt("LAST_MODIFIED_TIME", new Date());
        mapper.selectList(queryWrapper).forEach(p -> {
            mapper.deleteById(p.getProgramId());
            System.err.println(p.getProgramId() + " delete");
        });
        QueryWrapper<ProgramCodeEntity> codeQueryWrapper= new QueryWrapper<>();
        codeQueryWrapper.eq("STATE", "00");
        codeQueryWrapper.lt("CREATE_TIME", new Date());
        codeMapper.selectList(codeQueryWrapper).forEach(p -> {
            codeMapper.deleteById(p.getProgramCodeId());
            System.err.println(p.getProgramCodeId() + " delete");
        });
    }

}

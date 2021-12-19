package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:10
 * @Description: 不同编程语言的启动接口，提供run方法
 */
public abstract class ProgramRunner {
    @Autowired
    private RunProgramConfig runProgramConfig;
    @Autowired
    private FileUtil fileUtil;
    /**
     * 运行程序,程序模板
     * @return
     */
    public String run(ProgramEntity entity){
        entity.setClassName("Solution");//程序的类名统一明明为Solution
        saveProgramToLocal(entity);//临时将程序储存至本地
        return runProgram(entity);//在本地运行程序，获得结果
    }

    /**
     * 运行程序,程序模板
     * @return
     */
    public String run(String code,String language){
        ProgramEntity entity = new ProgramEntity();
        entity.setCode(code);
        entity.setLanguage(language);
        return run(entity);
    }

    /**
     * 根据运行的操作系统和编程语言，调用不同的命令行参数，运行程序，获得结果
     * @param entity
     */
    private String runProgram(ProgramEntity entity) {
        try {
            ProgramRunnerDto programRunnerDto = paserProgramRunnerDto(entity);
            String result = runCMD(programRunnerDto);
            entity.setError(false);
            entity.setOutput(result);
        } catch (Exception e) {
            entity.setError(true);
            entity.setOutput(e.getMessage());
        }
        return entity.getOutput();
    }

    /**
     * 将要运行的程序保存至本地
     * @param entity
     */
    private void saveProgramToLocal(ProgramEntity entity) {
        if (!SupportLanguage.isInclude(entity.getLanguage())) {
            throw new RuntimeException("不支持的语言类型");
        }
        String path = runProgramConfig.getPath();
        path += entity.getClassName() + SupportLanguage.valueOf(entity.getLanguage()).getSuffix();
        try {
            String codeToSave = programWrapper(entity);
            fileUtil.saveToPath(path, codeToSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ProgramEntity抽取表示程序运行相关信息的ProgramRunnerDto
     * @param entity
     * @return ProgramRunnerDto
     */
    private ProgramRunnerDto paserProgramRunnerDto(ProgramEntity entity) {
        String path = runProgramConfig.getPath();
        String fileName = entity.getClassName() + SupportLanguage.valueOf(entity.getLanguage()).getSuffix();
        ProgramRunnerDto programRunnerDto = new ProgramRunnerDto();
        programRunnerDto.setClassName(entity.getClassName());
        programRunnerDto.setFileName(fileName);
        programRunnerDto.setInput(entity.getInput());
        programRunnerDto.setPath(path);
        return programRunnerDto;
    }

    /**
     * 调用命令行运行程序
     * @param dto
     * @return
     */
    public abstract String runCMD(ProgramRunnerDto dto);

    /**
     * 根据用户输入code，组装成真正能运行的程序文件
     * @param entity
     * @return
     */
    public abstract String programWrapper(ProgramEntity entity);
}

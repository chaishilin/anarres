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
        savePaseredCode(entity);//临时将程序储存至本地
        return runProgram(entity);//在本地运行程结果
    }

    /**
     * 运行输入的代码
     * @param code
     * @return
     */
    public String run(String code){
        ProgramEntity entity = new ProgramEntity();
        entity.setClassName("Solution");//程序的类名统一明明为Solution
        entity.setCode(code);
        entity.setLanguage(getLanguage());
        saveCode(entity);
        return runProgram(entity);
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
     * 将要运行的程序经过模板解析后，保存至本地
     * @param entity
     */
    private void savePaseredCode(ProgramEntity entity) {
        String path = getSavePath(entity);
        try {
            fileUtil.saveToPath(path, programWrapper(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将要运行的程序直接保存至本地
     * @param entity
     */
    private void saveCode(ProgramEntity entity) {
        String path = getSavePath(entity);
        try {
            fileUtil.saveToPath(path, entity.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得保存路径
     * @param entity
     * @return
     */
    private String getSavePath(ProgramEntity entity) {
        String path = runProgramConfig.getPath();
        path += entity.getClassName() + SupportLanguage.valueOf(entity.getLanguage()).getSuffix();
        return path;
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

    /**
     * 获得编程语言名称
     * @return
     */
    public abstract String getLanguage();
}

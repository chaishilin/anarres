package com.csl.anarres.utils.ProgramRunner;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.dto.ProgramDto;
import com.csl.anarres.dto.ProgramRunnerDto;
import com.csl.anarres.entity.DataTypeEntity;
import com.csl.anarres.entity.ProgramInterface;
import com.csl.anarres.enums.SupportLanguage;
import com.csl.anarres.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

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
    public ProgramDto runWithTemplate(ProgramDto dto){
        dto.setClassName("Solution");//程序的类名统一明明为Solution
        savePaseredCode(dto);//临时将程序储存至本地
        return runProgram(dto);//在本地运行程结果
    }

    /**
     * 运行程序,程序模板
     * @return
     */
    public ProgramDto runWithTemplate(String code){
        return runWithTemplate(code,null);
    }

    /**
     * 运行程序,程序模板
     * @return
     */
    public ProgramDto runWithTemplate(String code,String input){
        ProgramDto dto = new ProgramDto();
        dto.setCode(code);
        dto.setInput(input);
        dto.setLanguage(getLanguage());
        return runWithTemplate(dto);
    }

    /**
     * 运行程序,程序模板
     * @return
     */
    public ProgramDto run(String code){
        return run(code,null);//在本地运行程结果
    }

    /**
     * 运行输入的代码
     * @param code
     * @param input
     * @return
     */
    public ProgramDto run(String code, String input){
        ProgramDto outputDto;
        ProgramDto dto = new ProgramDto();
        dto.setClassName("Solution");//程序的类名统一明明为Solution
        dto.setCode(code);
        dto.setInput(input);
        dto.setLanguage(getLanguage());
        saveCode(dto);
        outputDto = runProgram(dto);
        return outputDto;
    }

    /**
     * 根据运行的操作系统和编程语言，调用不同的命令行参数，运行程序，获得结果
     * @param dto
     */
    private ProgramDto runProgram(ProgramDto dto) {
        try {
            ProgramRunnerDto programRunnerDto = paserProgramRunnerDto(dto);
            String result = runCMD(programRunnerDto);
            dto.setError(false);
            dto.setOutput(result);
        } catch (Exception e) {
            dto.setError(true);
            dto.setOutput(e.getMessage());
        }
        return dto;
    }

    /**
     * 将要运行的程序经过模板解析后，保存至本地
     * @param dto
     */
    private void savePaseredCode(ProgramDto dto) {
        String path = getSavePath(dto);
        try {
            fileUtil.saveToPath(path, programWrapper(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将要运行的程序直接保存至本地
     * @param dto
     */
    private void saveCode(ProgramDto dto) {
        String path = getSavePath(dto);
        try {
            fileUtil.saveToPath(path, dto.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得保存路径
     * @param dto
     * @return
     */
    private String getSavePath(ProgramDto dto) {
        String path = runProgramConfig.getPath();
        path += dto.getClassName() + SupportLanguage.valueOf(dto.getLanguage()).getSuffix();
        return path;
    }

    /**
     * 根据ProgramEntity抽取表示程序运行相关信息的ProgramRunnerDto
     * @param dto
     * @return ProgramRunnerDto
     */
    private ProgramRunnerDto paserProgramRunnerDto(ProgramDto dto) {
        String path = runProgramConfig.getPath();
        String fileName = dto.getClassName() + SupportLanguage.valueOf(dto.getLanguage()).getSuffix();
        ProgramRunnerDto programRunnerDto = new ProgramRunnerDto();
        programRunnerDto.setClassName(dto.getClassName());
        programRunnerDto.setFileName(fileName);
        programRunnerDto.setInput(dto.getInput());
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
    public String programWrapper(ProgramInterface entity){
        String template = chooseTemplate();//todo 如何传参数说明选择用哪个模板？
        template = template.replace("{{FunctionBody}}",getFunctionBody(entity));
        template = template.replace("{{FunctionName}}",getFunctionName(entity));
        template = template.replace("{{Parameters}}",getParameters(entity));
        return template;
    }

    /**
     * 该语言的简单函数体
     * @return
     */
    public abstract String simpleFunction();

    /**
     * 生成(单个)函数入参
     * @param dataTypeEntity
     * @return
     */
    private String generateParam(DataTypeEntity dataTypeEntity){
        return dataTypeEntity.getDefinition() + " a";
    }

    /**
     * 生成(单个)函数入参
     * @param dataTypeEntity
     * @return
     */
    private String generateParamWithName(DataTypeEntity dataTypeEntity, String name){
        return generateParam(dataTypeEntity) + name;
    }

    /**
     * 生成(多个)函数入参
     * @param entities
     * @return
     */
    public String generateParams(List<DataTypeEntity> entities){
        StringBuilder result = new StringBuilder();
        entities.stream()
                .map(entity -> result.append(generateParamWithName(entity,entities.indexOf(entity)+"")+","))
                .collect(Collectors.toList())
                ;
        return result.toString().substring(0,result.length()-1);
    }

    /**
     * 根据输入的参数类型，生成简单函数体
     * @return
     */
    public String generateSimpleFunction(DataTypeEntity dataTypeEntity){
        String funcBody = simpleFunction();
        String result = funcBody.replace("{{params}}", generateParam(dataTypeEntity));
        return result;
    }

    /**
     * 选择程序模板
     * @return
     */
    protected abstract String chooseTemplate();

    /**
     * 选择默认程序模板
     * @return
     */
    protected abstract String chooseDeaultTemplate();

    /**
     * 获得编程语言名称
     * @return
     */
    public abstract String getLanguage();

    /**
     * 获得函数体
     * @param entity
     * @return
     */
    public String getFunctionBody(ProgramInterface entity){
        return entity.getCode();
    }

    /**
     * 获得函数名称
     * @param entity
     * @return
     */
    public abstract String getFunctionName(ProgramInterface entity);

    /**
     * 获得函数参数
     * @param entity
     * @return
     */
    public String getParameters(ProgramInterface entity){
        if(entity.getInput() != null && !"".equals(entity.getInput())){
            return entity.getInput().replace(" ",",");
        }
        return "";
    }
}

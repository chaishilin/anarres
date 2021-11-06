package com.csl.anarres.utils;

import com.csl.anarres.config.RunProgramConfig;
import com.csl.anarres.entity.ProgramEntity;
import com.csl.anarres.enums.SupportLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 21:11
 * @Description:
 */
@Component
public class ProgramUtils {
    @Autowired
    RunProgramConfig runProgramConfig;
    @Autowired
    FileUtil fileUtil;

    public String programWrapper(ProgramEntity entity){
        String relativePath = runProgramConfig.getRelativePath();
        String codeToSave = null;
        String defName = null;
        String code = entity.getCode();
        switch (SupportLanguage.valueOf(entity.getLanguage())) {
            case java:
                StringBuilder sStart = fileUtil.readFromClasspath(relativePath + "\\" + "SolutionStart.java");
                StringBuilder sEnd = fileUtil.readFromClasspath(relativePath + "\\" + "SolutionEnd.java");
                codeToSave = sStart.append(code).append(sEnd).toString();
                break;
            case golang:
                defName = code.split("\\(")[0].replace("func ","");
                StringBuilder goTemplate = fileUtil.readFromClasspath(relativePath + "\\" + "Solution.go");
                goTemplate.append(code);
                String goTemplateString = goTemplate.toString();
                goTemplateString = goTemplateString.replace("inputYourFunction",defName);
                StringBuilder inputStaff = new StringBuilder();
                inputStaff.append("fmt.Println(\"Call Function : "+defName+"\")\n");
                inputStaff.append("fmt.Println(\"--------\")\n");
                goTemplateString = goTemplateString.replace("inputStaff",inputStaff.toString());
                StringBuilder outputStaff = new StringBuilder();
                outputStaff.append("fmt.Println(\"--------\")\n");
                outputStaff.append("fmt.Println(\"output:\")\n");
                goTemplateString = goTemplateString.replace("outputStaff",outputStaff.toString());
                codeToSave = goTemplateString;
                break;
            case python:
                StringBuilder pythonTemplate = new StringBuilder();
                pythonTemplate.append(code);
                pythonTemplate.append("\n");
                pythonTemplate.append(fileUtil.readFromClasspath(relativePath + "\\" + "Solution.py"));
                defName = code.split("\\(")[0].replace("def ","");
                StringBuilder runDefStr = new StringBuilder();
                //组成调用函数的那一行
                runDefStr.append(defName);
                runDefStr.append("(");
                if(entity.getInput() != null && !"".equals(entity.getInput())){
                    runDefStr.append(entity.getInput().replace(" ",","));
                }
                runDefStr.append(")");
                runDefStr.append("\n");

                codeToSave = pythonTemplate.toString().replace("inputYourFunction",runDefStr.toString());
                break;
        }
        return codeToSave;
    }
    public void genarateClassName(ProgramEntity entity){
        entity.setClassName("Solution");
    }
}

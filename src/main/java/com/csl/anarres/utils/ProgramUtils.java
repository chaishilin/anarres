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
        StringBuilder sStart = null;
        StringBuilder sEnd = null;
        String codeToSave = null;
        switch (SupportLanguage.valueOf(entity.getLanguage())) {
            case java:
                sStart = fileUtil.readFromClasspath(relativePath + "\\" + "SolutionStart.java");
                sEnd = fileUtil.readFromClasspath(relativePath + "\\" + "SolutionEnd.java");
                codeToSave = sStart.append(entity.getCode()).append(sEnd).toString();
                break;
            case python:
                String code = entity.getCode();
                String defName = code.split("\\(")[0].replace("def ","");
                StringBuilder runDefStr = new StringBuilder();
                runDefStr.append("print(\"Call Funtion : "+ defName + "\")");
                runDefStr.append("\n");
                runDefStr.append("print(\"--------\")");
                runDefStr.append("\n");
                //组成调用函数的那一行
                runDefStr.append("result = ");
                runDefStr.append(defName);
                runDefStr.append("(");
                runDefStr.append(entity.getInput().replace(" ",","));
                runDefStr.append(")");
                runDefStr.append("\n");
                //输出output
                runDefStr.append("print(\"--------\")");
                runDefStr.append("\n");
                runDefStr.append("print(\"output:\")");
                runDefStr.append("\n");
                runDefStr.append("print(result)");
                runDefStr.append("\n");

                codeToSave = entity.getCode() + "\n"+runDefStr.toString();
                break;
        }
        return codeToSave;
    }
    public void genarateClassName(ProgramEntity entity){
        entity.setClassName("Solution");
    }
}

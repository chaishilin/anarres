package com.csl.anarres.utils;

import com.csl.anarres.exception.RunProgramException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 21:46
 * @Description:
 */
public class CMDUtils {
    public static String execCMD(String command){
        StringBuilder result = new StringBuilder();
        StringBuilder errorResult = new StringBuilder();
        Process process = null;
        command = "cmd.exe /c " + command;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                result.append(line+"\n");
            }

            BufferedReader bufferedErrorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(),"GBK"));
            String errorLine = null;
            while ((errorLine = bufferedErrorReader.readLine()) != null){
                errorResult.append(errorLine+"\n");
            }

            if(errorResult.toString() != null && !"".equals(errorResult.toString())) {
                throw new RunProgramException(errorResult.toString());
            }
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new RunProgramException(e.getMessage());
        }
    }
}

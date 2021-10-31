package com.csl.anarres.utils;

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
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

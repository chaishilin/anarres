package com.csl.anarres.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/19 17:14
 * @Description:
 */
@Component
public class RequestUtil {
    public static String readFromInputStream(HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        String line = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            while ((line = bufferedReader.readLine())!= null){
                result.append(line);
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result.toString();
    }
}

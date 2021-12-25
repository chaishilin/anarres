package com.csl.anarres.utils;

import com.csl.anarres.config.RunProgramConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/2 14:11
 * @Description:
 */
@Component
public class FileUtil {
    @Autowired
    RunProgramConfig runProgramConfig;
    public Path saveToPath(String path, String code){
        Path result = null;
        try{
            result = Files.write(Paths.get(path), code.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
//    public StringBuilder readFromClasspath(String path){
//        StringBuilder result = new StringBuilder();
//        try {
//            File resourcefile = ResourceUtils.getFile("classpath:"+path);
//            System.out.println("readFromClasspath:" + "classpath:"+path);
//            BufferedReader br = new BufferedReader(new FileReader(resourcefile));
//            String temp;
//            while((temp=br.readLine())!=null){
//                result.append(temp+"\n");
//            }
//            System.out.println("readFromClasspath:" + result.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return result;
//    }

    public StringBuilder readFromClasspath(String path){
        StringBuilder result = new StringBuilder();
        System.out.println("readFromClasspath "+path);
        try {
            Resource resource = new DefaultResourceLoader().getResource("classpath:" + path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

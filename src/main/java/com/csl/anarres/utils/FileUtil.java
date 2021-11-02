package com.csl.anarres.utils;

import com.csl.anarres.config.RunProgramConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    public StringBuilder readFromClasspath(String path){
        StringBuilder result = new StringBuilder();
        try {
            File resourcefile = ResourceUtils.getFile("classpath:"+path);
            BufferedReader br = new BufferedReader(new FileReader(resourcefile));
            String temp;
            while((temp=br.readLine())!=null){
                result.append(temp+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void deleteProgramFromTargetPath(){
        String path = runProgramConfig.getPath();
        File dir = new File(path);
        File[] files = dir.listFiles();
        for(File f:files){
            CMDUtils.execCMD( "del " + f.getPath());
        }
    }
}

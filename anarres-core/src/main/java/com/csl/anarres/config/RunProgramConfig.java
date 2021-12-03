package com.csl.anarres.config;

import com.csl.anarres.enums.OsType;
import com.csl.anarres.utils.CMDUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 22:09
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix="run-program-config")
public class RunProgramConfig {
    private String relativePath;
    private int timeout;
    public String getPath() {
        if(CMDUtils.systemType().equals(OsType.Windows)){
            ApplicationHome h = new ApplicationHome(getClass());
            String pathCurrent = h.getSource().getParent();
            return pathCurrent+"\\"+relativePath+"\\";
        }else{
            return "/root/programs/";
        }
    }

}

package com.csl.anarres.config;

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
public class runProgramConfig {
    private String relativePath;

    public String getPath() {
        ApplicationHome h = new ApplicationHome(getClass());
        String pathCurrent = h.getSource().getParent();
        return pathCurrent+"\\"+relativePath+"\\";
    }

}

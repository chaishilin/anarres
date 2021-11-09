package com.csl.anarres.config;

import com.csl.anarres.annotation.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 22:33
 * @Description:
 */
@Configuration
public class DefaultWebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private TokenArgumentResolver tokenArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(tokenArgumentResolver);
    }
}

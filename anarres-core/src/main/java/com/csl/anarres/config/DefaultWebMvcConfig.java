package com.csl.anarres.config;

import com.csl.anarres.aspect.PaserUserStateResolver;
import com.csl.anarres.intercepter.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
    private PaserUserStateResolver paserUserStateResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(paserUserStateResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AuthorizationInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/login");
        registration.excludePathPatterns("/logout");
        registration.excludePathPatterns("/register");
        registration.excludePathPatterns("/resetPassword");
        registration.excludePathPatterns("/programList");
        registration.excludePathPatterns("/doRemoteProgram");
    }
}

package com.csl.anarres.config;

import com.csl.anarres.intercepter.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录拦截器设置
 * @author: Shilin Chai
 * @Date: 2021/10/28 18:18
 * @Description:
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new AuthorizationInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/login");
        registration.excludePathPatterns("/register");
        registration.excludePathPatterns("/resetPassword");
        registration.excludePathPatterns("/programList/");

    }
}

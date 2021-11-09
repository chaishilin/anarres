package com.csl.anarres.annotation;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 22:22
 * @Description:
 */
@Component
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private LoginService loginService;

    /**
     * 入参筛选，看有没有userOnly的注解
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserSelfOnly.class) || parameter.hasMethodAnnotation(UserSelfOnly.class);
    }

    /**
     * @param parameter     入参
     * @param mavContainer  model 和 view
     * @param webRequest    web相关
     * @param binderFactory 入参解析
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        String json = readFromInputStream(request);//在request的inputStream中获取json字符串
        Object result = JSONObject.parseObject(json, parameter.getParameterType());//利用fastJson，根据字符串生成对象
        //迄今为止，类似于实现了@requestBody的功能
        UserEntity user = loginService.getUserInfo(request);//利用token获得userEntity
        Field createrId = result.getClass().getDeclaredField("createrId");//获取对象的createrId属性
        createrId.setAccessible(true);
        createrId.set(result,user.getUserId());//设置请求对象的createrId为token对应的userId
    return result;
    }

    private String readFromInputStream(HttpServletRequest request) {
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


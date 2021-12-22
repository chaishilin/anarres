package com.csl.anarres.aspect;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.annotation.PaserUserState;
import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.LoginService;
import com.csl.anarres.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/8 22:22
 * @Description:
 */
@Component
public class PaserUserStateResolver implements HandlerMethodArgumentResolver {
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
        return parameter.hasParameterAnnotation(PaserUserState.class);
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
        String json = RequestUtil.readFromInputStream(request);//在request的inputStream中获取json字符串
        Object result = JSONObject.parseObject(json, parameter.getParameterType());//利用fastJson，根据字符串生成对象
        //到此，类似于实现了@requestBody的功能
        UserEntity user = loginService.getUserInfo(request);//利用token获得userEntity
        if(user != null){
            String userId = user.getUserId();
            setDeclaredBoolField(result,"isLogin",true);
            String createrId =(String) getDeclaredField(result,"createrId");//获取对象的createrId属性
            setDeclaredStringField(result,"userId",userId);
            if(createrId == null || !createrId.equals(userId)){
                setDeclaredBoolField(result,"isSelf",false);
            }else{
                setDeclaredBoolField(result,"isSelf",true);
            }
        }else{
            setDeclaredBoolField(result,"isLogin",false);
        }//这边是设置是成功的
    return result;
    }

    private void setDeclaredStringField(Object o,String fieldName,String value) throws IllegalAccessException {
        try {
            Field field = findField(o.getClass(),fieldName);//获取对象的field属性
            field.setAccessible(true);
            field.set(o,value);
        }catch (NoSuchFieldException ignored){
        }
    }

    private void setDeclaredBoolField(Object o,String fieldName,boolean value) throws IllegalAccessException {
        try {
            Field field = findField(o.getClass(),fieldName);//获取对象的field属性
            field.setAccessible(true);
            field.set(o,value);
        }catch (NoSuchFieldException ignored){
        }
    }
    private Object getDeclaredField(Object o,String fieldName) throws IllegalAccessException {
        try {
            Field field = findField(o.getClass(),fieldName);//获取对象的属性
            field.setAccessible(true);
            return  field.get(o);
        }catch (NoSuchFieldException ignore){
            return null;
        }
    }
    //todo field 相关方法独立
    private Field findField(Class<?> clazz, String fieldName) throws NoSuchFieldException{
        Field result = null;
        if(clazz == null){
            throw new NoSuchFieldException();
        }
        try {
            result = clazz.getDeclaredField(fieldName);//寻找类本身的属性
        }catch (NoSuchFieldException ignored){
        }
        if(result != null){
            return result;
        }
        try {
            result = clazz.getField(fieldName);//寻找父类的公有属性
        }catch (NoSuchFieldException ignored){
        }
        if(result != null){
            return result;
        }
        return findField(clazz.getSuperclass(),fieldName);//递归向上查找父类
    }



}


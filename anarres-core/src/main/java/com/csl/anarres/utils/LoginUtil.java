package com.csl.anarres.utils;

import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/24 10:56
 * @Description:
 */
@Component
public class LoginUtil {
    @Autowired
    private LoginService loginService;

    public UserEntity getCurrentUser(HttpServletRequest request){
        return loginService.getUserInfo(request);
    }

    public UserEntity getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        assert request != null;
        return getCurrentUser(request);
    }

    public UserEntity getCurrentUserOrPublic(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        assert request != null;
        return getCurrentUserOrPublic(request);
    }

    public UserEntity getCurrentUserOrPublic(HttpServletRequest request){
        UserEntity result = getCurrentUser(request);
        if(result == null){
            result = new UserEntity();
            result.setUserId("public");
        }
        return result;
    }
}

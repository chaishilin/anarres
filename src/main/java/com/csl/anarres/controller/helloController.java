package com.csl.anarres.controller;

import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class helloController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/find")
    @ResponseBody
    public String find(){
        try {
            List<UserEntity> userEntityList = userService.find();
            return userEntityList.toString();
        }catch (Exception e){
            return  e.getMessage();
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(UserEntity user){
        try {
            UserEntity entity = userService.login(user);
            return entity.toString();
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @PostMapping("/register")
    @ResponseBody
    public String register(UserEntity user){
        try {
            UserEntity entity = userService.register(user);
            return entity.toString();
        }catch (Exception e){
            return e.getMessage();
        }
    }
}

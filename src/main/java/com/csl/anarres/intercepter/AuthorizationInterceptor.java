package com.csl.anarres.intercepter;

import com.alibaba.fastjson.JSONObject;
import com.csl.anarres.utils.RedisUtil;
import com.csl.anarres.utils.ResponseTemplate;
import com.csl.anarres.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/28 17:24
 * @Description:
 */
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String userId = "";
        PrintWriter printWriter = null;
        String token  = request.getHeader("token");
        Jedis jedis = RedisUtil.getInstance();
        try {
            if(token != null && !token.equals("null")){
                userId = jedis.get(token);
            }
            if(token == null || "".equals(token) || userId == null || "".equals(userId)){
                printWriter = response.getWriter();
                ResponseTemplate responseMsg = ResponseUtil.fail(HttpStatus.UNAUTHORIZED.value(),"please login");
                printWriter.println(JSONObject.toJSONString(responseMsg));
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != printWriter){
                printWriter.flush();
                printWriter.close();
            }
        }
        request.setAttribute("user",userId);
        return true;

    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
        return;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
    }


}

package com.tp.wedding.config;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.common.JsonUtils;
import com.tp.wedding.service.ManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.annotation.Annotation;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        boolean mark = requestUri.endsWith("login")
                || requestUri.endsWith("logout")
                || requestUri.endsWith("register")
                || requestUri.endsWith("reset")
                || requestUri.endsWith("updatePWD");
        if(mark){
            return true;
        }

        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Annotation annotation = handlerMethod.getMethodAnnotation(TokenValidate.class);
        if(annotation!= null){
            HttpSession publicSession = managerService.getPublicSession();
            if(publicSession==null){
                JsonResult result = JsonResult.build(JsonResult.STATUS_NOLOGIN, "尚未登录");
                this.print(JsonUtils.objectToJson(result),response);
                return false;
            }
            String token = managerService.getParam(request,"token");
            if(StringUtils.isBlank(token)){
                JsonResult jsonResult = JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数缺失");
                this.print(JsonUtils.objectToJson(jsonResult),response);
                return false;
            }
            String userId = managerService.getUserIdByToken(token);
            if(StringUtils.isBlank(userId)){
                JsonResult jsonResult = JsonResult.build(JsonResult.STATUS_NOLOGIN,"尚未登录");
                this.print(JsonUtils.objectToJson(jsonResult),response);
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    //向前端输出json格式的内容
    private void print(String output, HttpServletResponse response)
    {
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().print(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

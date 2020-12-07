package com.tp.wedding.config;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.common.JsonUtils;
import com.tp.wedding.common.Role;
import com.tp.wedding.entity.User;
import com.tp.wedding.service.ManagerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        AdminValidate annotation = ((HandlerMethod) handler).getMethodAnnotation(AdminValidate.class);
        if(annotation!= null){
            String token = managerService.getParam(request,"token");
            if(StringUtils.isBlank(token)){
                JsonResult jsonResult = JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数缺失");
                this.print(JsonUtils.objectToJson(jsonResult),response);
                return false;
            }
            User user = managerService.getByToken(token);
            if(user == null){
                JsonResult jsonResult = JsonResult.build(JsonResult.STATUS_NOLOGIN,"尚未登录");
                this.print(JsonUtils.objectToJson(jsonResult),response);
                return false;
            }
            Integer roleIndex = user.getRoleIndex();
            Role userRole= Role.getByIndex(roleIndex);
            if(userRole == null){
                JsonResult jsonResult = JsonResult.build(JsonResult.STATUS_FORBIDDEN,"没有权限");
                this.print(JsonUtils.objectToJson(jsonResult),response);
                return false;
            }
            Role[] roles = annotation.roles();
            boolean flag = false;
            for(Role role:roles){
                if(role == userRole){
                    flag = true;
                }
            }
            if(!flag){
                return false;
            }
        }

        return super.preHandle(request, response, handler);
    }

    private void print(String output,HttpServletResponse response){
        try{
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

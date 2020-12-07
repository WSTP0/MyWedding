package com.tp.wedding.service;

import com.tp.wedding.common.Constants;
import com.tp.wedding.common.JsonUtils;
import com.tp.wedding.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ManagerService {

    @Autowired
    private RedisService redisService;

    public Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue != null) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    public void setPublicSession(HttpSession publicSession){
        redisService.set(Constants.PUBLIC_SESSION_KEY,JsonUtils.objectToJson(publicSession),10L, TimeUnit.MINUTES);
    }

    public HttpSession getPublicSession(){
        HttpSession publicSession = JsonUtils.jsonToPojo((String) redisService.get(Constants.PUBLIC_SESSION_KEY), HttpSession.class);
        return publicSession;
    }

    public void setAttribute(String name,Object value){
        HttpSession publicSession = this.getPublicSession();
        if(publicSession != null){
            publicSession.setAttribute(name, value);
        }
    }

    public Object getAttribute(String name){
        HttpSession publicSession = this.getPublicSession();
        if(publicSession != null){
            return publicSession.getAttribute(name);
        }
        return null;
    }

    public String getParam(HttpServletRequest request,String key){
        Map<String, Object> map = this.getRequestParams(request);
        if(map!=null && map.size()>0){
            Object tokenObj =  map.get(key);
            if(tokenObj!=null){
                return (String)tokenObj;
            }
        }
        return null;
    }

    public User getByToken(String token){
        return JsonUtils.jsonToPojo((String) redisService.get(token), User.class);
    }

    public String getUserIdByToken(String token){
        User user = getByToken(token);
        if(user != null){
            return user.getUserId();
        }
        return null;
    }

}

package com.tp.wedding.controller;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dto.MenuDto;
import com.tp.wedding.service.ManagerService;
import com.tp.wedding.service.UserMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userMenu")
public class UserMenuController {

    @Autowired
    private UserMenuService userMenuService;
    @Autowired
    private ManagerService managerService;

    /**
     * 给用户分配菜单，以逗号形式隔开
     * @param token
     * @param menuStr
     * @return
     */
    @RequestMapping("/distributeMenu")
    public JsonResult distributeMenu(String token,String menuStr){
        if(StringUtils.isBlank(token)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        String userId = managerService.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"用户不存在");
        }
        return userMenuService.distributeMenu(userId,menuStr);
    }

    /**
     * 移除用户的菜单
     * @param token
     * @param menuStr
     * @return
     */
    @RequestMapping("/removeMenu")
    public JsonResult removeMenu(String token,String menuStr){
        if(StringUtils.isBlank(token)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        String userId = managerService.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"用户不存在");
        }
        return userMenuService.removeMenu(userId,menuStr);
    }

    /**
     * 查询用户菜单关系
     * @param token
     * @return
     */
    @RequestMapping("/queryUserMenus")
    public JsonResult queryUserMenus(String token){
        if(StringUtils.isBlank(token)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        String userId = managerService.getUserIdByToken(token);
        if(StringUtils.isBlank(userId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"用户不存在");
        }
        return userMenuService.queryUserMenus(userId);
    }

}

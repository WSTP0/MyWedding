package com.tp.wedding.service;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dao.UserMenuDao;
import com.tp.wedding.entity.UserMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserMenuService {

    @Autowired
    private UserMenuDao userMenuDao;

    public JsonResult distributeMenu(String userId,String menuStr){
        try{
            if(StringUtils.isBlank(menuStr)){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
            }
            String[] menus = menuStr.split(",");
            if(menus != null && menus.length>0){
                for(String menuId:menus){
                    UserMenu userMenu = new UserMenu();
                    userMenu.setMenuId(menuId);
                    userMenu.setUserId(userId);
                    userMenu.setCreateTime(new Date());
                    userMenu.setUpdateTime(new Date());
                    userMenuDao.save(userMenu);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
        return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
    }

    public JsonResult removeMenu(String userId,String menuStr){
        try{
            if(StringUtils.isBlank(menuStr)){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
            }
            String[] menus = menuStr.split(",");
            List<String> menuList = Arrays.asList(menus);
            if(menuList != null && menuList.size()>0){
                userMenuDao.deleteByUserIdAndMenuIds(userId,menuList);
                return JsonResult.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
        return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
    }

    public JsonResult queryUserMenus(String userId){
        try{
            return JsonResult.ok(userMenuDao.queryByUserId(userId));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public List<UserMenu> getUserMenus(String userId){
        return userMenuDao.queryByUserId(userId);
    }

}

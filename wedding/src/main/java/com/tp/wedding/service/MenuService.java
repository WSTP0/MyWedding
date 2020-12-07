package com.tp.wedding.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dao.MenuDao;
import com.tp.wedding.dto.MenuDto;
import com.tp.wedding.entity.Clothing;
import com.tp.wedding.entity.Menu;
import com.tp.wedding.entity.UserMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserMenuService userMenuService;

    public JsonResult add(MenuDto menuDto){
        try{
            Menu menu = new Menu();
            BeanUtil.copyProperties(menuDto, menu, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            menu.setCreateTime(new Date());
            menu.setUpdateTime(new Date());
            menuDao.save(menu);
            return JsonResult.ok(menu);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult update(MenuDto menuDto){
        try{
            Menu menu = new Menu();
            BeanUtil.copyProperties(menuDto, menu, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            menu.setUpdateTime(new Date());
            menuDao.save(menu);
            return JsonResult.ok(menu);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult delete(String menuId){
        try{
            Menu menu = menuDao.queryByMenuId(menuId);
            menu.setIsDelete(1);
            menu.setUpdateTime(new Date());
            return JsonResult.ok(menuDao.save(menu));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult findAll(){
        try{
            List<Menu> menus = menuDao.queryAll();
            return JsonResult.ok(menus);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    /**
     * 获取某个用户的菜单
     * @param userId
     * @return
     */
    public List<Menu> getMenus(String userId){
        List<UserMenu> userMenus =userMenuService.getUserMenus(userId);
        if(userMenus == null || userMenus.size()==0){
            return null;
        }
        List<String> menuIds = this.getMenuIds(userMenus);
        if(menuIds !=null ){
            return menuDao.queryByMenuIds(menuIds);
        }
        return null;
    }

    private List<String> getMenuIds(List<UserMenu> userMenus){
        List<String> menuIds = new ArrayList<String>();
        if(userMenus == null || userMenus.size()==0){
            return null;
        }
        for(UserMenu userMenu : userMenus){
            menuIds.add(userMenu.getMenuId());
        }
        return menuIds;
    }

}

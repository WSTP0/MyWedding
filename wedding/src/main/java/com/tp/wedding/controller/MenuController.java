package com.tp.wedding.controller;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dto.MenuDto;
import com.tp.wedding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/add")
    public JsonResult add(MenuDto menuDto){
        return menuService.add(menuDto);
    }

    @RequestMapping("/update")
    public JsonResult update(MenuDto menuDto){
        return menuService.update(menuDto);
    }

    @RequestMapping("/delete")
    public JsonResult delete(String menuId){
        return menuService.delete(menuId);
    }

    @RequestMapping("/findAll")
    public JsonResult findAll(){
        return menuService.findAll();
    }

}

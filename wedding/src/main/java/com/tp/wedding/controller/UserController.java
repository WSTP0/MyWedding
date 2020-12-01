package com.tp.wedding.controller;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dto.ClothingDto;
import com.tp.wedding.dto.UserDto;
import com.tp.wedding.service.ClothingService;
import com.tp.wedding.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param userDto
     * @return
     */
    @RequestMapping("/register")
    public JsonResult register(UserDto userDto){
        return userService.register(userDto);
    }

    /**
     * 重置密码
     * @param userName
     * @return
     */
    @RequestMapping("/reset")
    public JsonResult reset(String userName){
        return userService.reset(userName);
    }

    /**
     * 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/updatePassword")
    public JsonResult updatePassword(String userId,String oldPassword,String newPassword){
        return userService.updatePassword(userId,oldPassword,newPassword);
    }

    /**
     * 修改基础信息
     * @param userDto
     * @return
     */
    @RequestMapping("/updateBaseInfo")
    public JsonResult updateBaseInfo(UserDto userDto){
        return userService.updateBaseInfo(userDto);
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping("/delete")
    public JsonResult delete(String userId){
        return userService.delete(userId);
    }

    @RequestMapping("/findOne")
    public JsonResult findOne(String userId){
        if(StringUtils.isBlank(userId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        return userService.findOne(userId);
    }

}

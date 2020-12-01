package com.tp.wedding.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tp.wedding.common.Constants;
import com.tp.wedding.common.JsonResult;
import com.tp.wedding.common.JsonUtils;
import com.tp.wedding.common.MD5Util;
import com.tp.wedding.dao.UserDao;
import com.tp.wedding.dto.UserDto;
import com.tp.wedding.entity.Clothing;
import com.tp.wedding.entity.Order;
import com.tp.wedding.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public JsonResult register(UserDto userDto){
        try{
            User user = new User();
            BeanUtil.copyProperties(userDto, user, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            user.setPassword(MD5Util.toEncryptionedPassword(Constants.INIT_PASSWORD));
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userDao.save(user);
            return JsonResult.ok(userDto);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult reset(String userName){
        try{
            User user = userDao.queryByUserName(userName);
            if(user == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"找不到用户名为"+userName+"的用户");
            }
            user.setPassword(MD5Util.toEncryptionedPassword(Constants.INIT_PASSWORD));
            user.setUpdateTime(new Date());
            userDao.save(user);
            return JsonResult.ok(user);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult updatePassword(String userId,String oldPassword,String newPassword){
        try{
            User user = userDao.queryByUserId(userId);
            if(user == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
            }
            String old = user.getPassword();
            if(!old.equals(MD5Util.toEncryptionedPassword(oldPassword))){//原密码不同
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"原密码有误");
            }
            user.setPassword(MD5Util.toEncryptionedPassword(newPassword));
            user.setUpdateTime(new Date());
            userDao.save(user);
            return JsonResult.ok(user);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult updateBaseInfo(UserDto userDto){
        try{
            User user = userDao.queryByUserId(userDto.getUserId());
            if(user == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
            }
            user.setMobile(userDto.getMobile());
            user.setNickName(userDto.getNickName());
            user.setSex(userDto.getSex());
            user.setUpdateTime(new Date());
            userDao.save(user);
            return JsonResult.ok(user);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult delete(String userId){
        try{
            User user = userDao.queryByUserId(userId);
            user.setIsDelete(1);
            user.setUpdateTime(new Date());
            return JsonResult.ok(userDao.save(user));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult findOne(String userId){
        if(StringUtils.isBlank(userId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        return JsonResult.ok(userDao.queryByUserId(userId));
    }

}

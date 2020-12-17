package com.tp.wedding.controller;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.common.Role;
import com.tp.wedding.config.AdminValidate;
import com.tp.wedding.dto.ClothingDto;
import com.tp.wedding.service.ClothingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clothing")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @RequestMapping("/add")
    public JsonResult add(ClothingDto clothingDto){
        return clothingService.add(clothingDto);
    }

    @RequestMapping("/update")
    public JsonResult update(ClothingDto clothingDto){
        return clothingService.update(clothingDto);
    }

    @RequestMapping("/delete")
    public JsonResult delete(String clothingCode){
        return clothingService.delete(clothingCode);
    }

    @RequestMapping("/findListByStatus")
    public JsonResult findListByStatus(String status, @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "20")Integer pageSize){
        return clothingService.findListByStatus(status,pageIndex,pageSize);
    }

    @RequestMapping("/findOne")
    public JsonResult findOne(String clothingCode){
        if(StringUtils.isBlank(clothingCode)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        return clothingService.findOne(clothingCode);
    }

    /**
     * 下架
     * @return
     */
    @AdminValidate(roles = {Role.ADMIN,Role.ROOT})
    @RequestMapping("/undercarriage")
    public JsonResult undercarriage(String clothingCode){
        if(StringUtils.isBlank(clothingCode)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        return clothingService.undercarriage(clothingCode);
    }

}

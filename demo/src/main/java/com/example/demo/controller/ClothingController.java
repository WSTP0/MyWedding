package com.example.demo.controller;

import com.example.demo.common.JsonResult;
import com.example.demo.dto.ClothingDto;
import com.example.demo.service.ClothingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clothing")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @RequestMapping("/update")
    public JsonResult add(ClothingDto clothingDto){
        return clothingService.save(clothingDto);
    }

    @RequestMapping("/delete")
    public JsonResult delete(String clothingId){
        return clothingService.delete(clothingId);
    }

    @RequestMapping("/findListByStatus")
    public JsonResult findListByStatus(String status,Integer pageIndex, Integer pageSize){
        return clothingService.findListByStatus(status,pageIndex,pageSize);
    }

    @RequestMapping("/findOne")
    public JsonResult findOne(String clothingId){
        if(StringUtils.isBlank(clothingId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        return clothingService.findOne(clothingId);
    }

}

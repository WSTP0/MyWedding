package com.tp.wedding.controller;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dto.ClothingDto;
import com.tp.wedding.service.ClothingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 下架
     * @return
     */
    @RequestMapping("/undercarriage")
    public JsonResult undercarriage(String clothingId){
        if(StringUtils.isBlank(clothingId)){
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"参数异常");
        }
        return clothingService.undercarriage(clothingId);
    }

}

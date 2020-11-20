package com.tp.wedding.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dao.ClothingDao;
import com.tp.wedding.dto.ClothingDto;
import com.tp.wedding.entity.Clothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClothingService {

    @Autowired
    private ClothingDao clothingDao;

    public JsonResult save(ClothingDto clothingDto){
        try{
            Clothing clothing = new Clothing();
            BeanUtil.copyProperties(clothingDto, clothing, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            clothingDao.save(clothing);
            return JsonResult.ok(clothing);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"保存失败");
        }
    }

    @Transactional
    public JsonResult delete(String clothingId){
        try{
            clothingDao.deleteByClothingId(clothingId);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"删除失败");
        }
        return JsonResult.ok();
    }

    public JsonResult findListByStatus(String status,Integer pageIndex, Integer pageSize){
        try{
            Pageable pageable = new PageRequest(pageIndex-1,pageSize);
            Page<Clothing> page =  clothingDao.findListByStatus(status,pageable);
            return JsonResult.ok(page);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"删除失败");
        }
    }

    public JsonResult findOne(String clothingId){
        try{
            return JsonResult.ok(clothingDao.queryByClothingId(clothingId));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"删除失败");
        }
    }

}

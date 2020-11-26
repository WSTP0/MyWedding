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

import java.util.Date;

@Service
public class ClothingService {

    @Autowired
    private ClothingDao clothingDao;

    public JsonResult add(ClothingDto clothingDto){
        try{
            Clothing clothing = new Clothing();
            BeanUtil.copyProperties(clothingDto, clothing, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            clothing.setCreateTime(new Date());
            clothing.setUpdateTime(new Date());
            clothingDao.save(clothing);
            return JsonResult.ok(clothing);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult update(ClothingDto clothingDto){
        try{
            Clothing clothing = new Clothing();
            BeanUtil.copyProperties(clothingDto, clothing, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            clothing.setUpdateTime(new Date());
            clothingDao.save(clothing);
            return JsonResult.ok(clothing);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    @Transactional
    public JsonResult delete(String clothingId){
        try{
            Clothing clothing = clothingDao.queryByClothingId(clothingId);
            clothing.setIsDelete(1);
            clothing.setUpdateTime(new Date());
            return JsonResult.ok(clothingDao.save(clothing));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult findListByStatus(String status,Integer pageIndex, Integer pageSize){
        try{
            Pageable pageable = new PageRequest(pageIndex-1,pageSize);
            Page<Clothing> page =  clothingDao.findListByStatus(status,pageable);
            return JsonResult.ok(page);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult findOne(String clothingId){
        try{
            return JsonResult.ok(clothingDao.queryByClothingId(clothingId));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    /**
     * 下架
     * @param clothingId
     * @return
     */
    @Transactional
    public JsonResult undercarriage(String clothingId){
        try{
            Clothing clothing = clothingDao.queryByClothingId(clothingId);
            clothing.setIsDelete(1);
            clothing.setUpdateTime(new Date());
            return JsonResult.ok(clothingDao.save(clothing));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

}

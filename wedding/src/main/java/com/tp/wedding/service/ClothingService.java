package com.tp.wedding.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dao.ClothingDao;
import com.tp.wedding.dto.ClothingDto;
import com.tp.wedding.entity.Clothing;
import com.tp.wedding.entity.OrderInfo;
import com.tp.wedding.entity.PackageInfo;
import com.tp.wedding.listener.OrderListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ClothingService implements OrderListener {

    @Autowired
    private ClothingDao clothingDao;
    @Autowired
    private PackageInfoService packageInfoService;

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
    public JsonResult delete(String clothingCode){
        try{
            Clothing clothing = clothingDao.queryByClothingCode(clothingCode);
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

    public JsonResult findOne(String clothingCode){
        try{
            return JsonResult.ok(clothingDao.queryByClothingCode(clothingCode));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    /**
     * 下架
     * @param clothingCode
     * @return
     */
    @Transactional
    public JsonResult undercarriage(String clothingCode){
        try{
            Clothing clothing = clothingDao.queryByClothingCode(clothingCode);
            clothing.setIsDelete(1);
            clothing.setUpdateTime(new Date());
            return JsonResult.ok(clothingDao.save(clothing));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    @Override
    public void afterOrderAdd(OrderInfo order) {

    }

    @Override
    public void afterOrderPayDeposit(OrderInfo order) {

    }

    @Override
    public void afterOrderPayBalance(OrderInfo order) {

    }

    @Override
    public void afterOrderSend(OrderInfo order) {
        List<Integer> ids = new ArrayList<Integer>();
        List<Clothing> clothingList = clothingDao.queryByIds(ids);
        if(clothingList !=null && clothingList.size()>0){
            for(Clothing clothing : clothingList){
                clothing.setStatus(1);
                clothing.setUpdateTime(new Date());
            }
        }
    }

    @Override
    public void afterOrderBack(OrderInfo order) {

    }

    @Override
    public void afterOrderFinish(OrderInfo order) {
        //租赁次数和状态有变化
        String packageIds = order.getPackageIds();
        List<PackageInfo> packageInfos = packageInfoService.queryByIds(packageIds);
        if(packageInfos!=null && packageInfos.size()>0){
            for(PackageInfo packageInfo:packageInfos){
                packageInfo.setLendCount(packageInfo.getLendCount()+1);
                packageInfo.setUpdateTime(new Date());
            }
        }
        String clothingIds = order.getClothingIds();
        List<Integer> clothingIdsList = this.getClothingIdList(clothingIds);
        if(clothingIdsList == null || clothingIdsList.size()==0){
            return;
        }
        List<Clothing> clothingList = clothingDao.queryByIds(clothingIdsList);
        if(clothingList !=null && clothingList.size()>0){
            for(Clothing clothing : clothingList){
                clothing.setLendCount(clothing.getLendCount()+1);
                clothing.setStatus(0);
                clothing.setUpdateTime(new Date());
            }
        }
    }

    private List<Integer> getClothingIdList(String clothingIds){
        List<Integer> clothingIdsList = new ArrayList<Integer>();
        if(StringUtils.isBlank(clothingIds)){
            return null;
        }
        String[] clothingIdArray = clothingIds.split(",");
        for(String clothingId:clothingIdArray){
            Integer id = Integer.valueOf(clothingId);
            clothingIdsList.add(id);
        }
        return clothingIdsList;
    }

    @Override
    public void afterOrderCancel(OrderInfo order) {
        List<Integer> ids = new ArrayList<Integer>();
        List<Clothing> clothingList = clothingDao.queryByIds(ids);
        if(clothingList !=null && clothingList.size()>0){
            for(Clothing clothing : clothingList){
                clothing.setStatus(0);
                clothing.setUpdateTime(new Date());
            }
        }
    }

}

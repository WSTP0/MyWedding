package com.tp.wedding.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dao.OrderDao;
import com.tp.wedding.dto.ClothingDto;
import com.tp.wedding.dto.OrderDto;
import com.tp.wedding.entity.Clothing;
import com.tp.wedding.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public JsonResult add(OrderDto orderDto){
        try{
            Order order = new Order();
            BeanUtil.copyProperties(orderDto, order, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            orderDao.save(order);
            return JsonResult.ok(order);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult update(OrderDto orderDto){
        try{
            Order order = new Order();
            BeanUtil.copyProperties(orderDto, order, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            order.setUpdateTime(new Date());
            orderDao.save(order);
            return JsonResult.ok(order);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    @Transactional
    public com.tp.wedding.common.JsonResult delete(String orderId){
        try{
            Order order = orderDao.queryByOrderId(orderId);
            order.setIsDelete(1);
            order.setUpdateTime(new Date());
            return JsonResult.ok(orderDao.save(order));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult findListByStatus(String status,Integer pageIndex, Integer pageSize){
        try{
            Pageable pageable = new PageRequest(pageIndex-1,pageSize);
            Page<Order> page =  orderDao.findListByStatus(status,pageable);
            return JsonResult.ok(page);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult findOne(String orderId){
        try{
            return JsonResult.ok(orderDao.queryByOrderId(orderId));
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

}

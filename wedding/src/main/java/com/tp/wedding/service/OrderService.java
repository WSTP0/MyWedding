package com.tp.wedding.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tp.wedding.common.DateUtils;
import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dao.OrderDao;
import com.tp.wedding.dto.OrderDto;
import com.tp.wedding.entity.OrderInfo;
import com.tp.wedding.listener.OrderListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private List<OrderListener> orderListenerList = new ArrayList<>();

    @Autowired
    private OrderDao orderDao;

    public void registerOrderListeners(List<OrderListener> orderListeners){
        orderListenerList.addAll(orderListeners);
    }

    public void afterOrderAdd(OrderInfo order){
        for(OrderListener orderListener:orderListenerList){
            orderListener.afterOrderAdd(order);
        }
    }

    public void afterOrderFinish(OrderInfo order){
        for(OrderListener orderListener:orderListenerList){
            orderListener.afterOrderFinish(order);
        }
    }

    public void afterOrderSend(OrderInfo order){
        for(OrderListener orderListener:orderListenerList){
            orderListener.afterOrderSend(order);
        }
    }

    public JsonResult add(OrderDto orderDto){
        try{
            if(StringUtils.isBlank(orderDto.getRenterName())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写租借人的名字");
            }
            if(StringUtils.isBlank(orderDto.getRenterPhone())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写租借人的手机号");
            }
            if(orderDto.getLendPrice() == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写租金");
            }
            if(orderDto.getDeposit() == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写定金");
            }
            if(orderDto.getBalance() == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写尾款+押金");
            }
            if(StringUtils.isBlank(orderDto.getPackageIds()) && StringUtils.isBlank(orderDto.getClothingIds())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写使用的套餐或自定的衣服");
            }
            if(StringUtils.isBlank(orderDto.getSource())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写渠道（客源）");
            }
            if(StringUtils.isBlank(orderDto.getUseAddress())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写使用的地址");
            }
            if(StringUtils.isBlank(orderDto.getUseStartTime())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写使用开始日期");
            }
            if(StringUtils.isBlank(orderDto.getUseEndTime())){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写使用结束日期");
            }

            OrderInfo order = new OrderInfo();
            BeanUtil.copyProperties(orderDto, order, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            order.setOrderId(UUID.randomUUID().toString());
            order.setStatus(0);
            order.setIsDelete(0);
            order.setOrderTime(new Date());
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            orderDao.save(order);
            //下单成功
            this.afterOrderAdd(order);
            return JsonResult.ok(order);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult payDeposit(String orderId){
        try{
            OrderInfo order = orderDao.queryByOrderId(orderId);
            if(order == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"该订单不存在");
            }
            order.setStatus(1);
            order.setUpdateTime(new Date());
            order.setPayDepositTime(new Date());
            orderDao.save(order);
            return JsonResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult payBalance(String orderId,String predictSendTime){
        try{
            if(StringUtils.isBlank(predictSendTime)){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"请填写预计发货的时间");
            }
            OrderInfo order = orderDao.queryByOrderId(orderId);
            if(order == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"该订单不存在");
            }
            order.setStatus(2);
            order.setUpdateTime(new Date());
            order.setPayBalanceTime(new Date());
            order.setPredictSendTime(DateUtils.getDateByStr(predictSendTime));
            orderDao.save(order);
            return JsonResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult send(String orderId){
        try{
            OrderInfo order = orderDao.queryByOrderId(orderId);
            if(order == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"该订单不存在");
            }
            order.setStatus(3);
            order.setUpdateTime(new Date());
            order.setSendTime(new Date());
            orderDao.save(order);
            this.afterOrderSend(order);
            return JsonResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult back(String orderId){
        try{
            OrderInfo order = orderDao.queryByOrderId(orderId);
            if(order == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"该订单不存在");
            }
            order.setStatus(4);
            order.setUpdateTime(new Date());
            order.setBackTime(new Date());
            orderDao.save(order);
            return JsonResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult finish(String orderId){
        try{
            OrderInfo order = orderDao.queryByOrderId(orderId);
            if(order == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"该订单不存在");
            }
            order.setStatus(5);
            order.setUpdateTime(new Date());
            order.setFinishTime(new Date());
            orderDao.save(order);
            this.afterOrderFinish(order);
            return JsonResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult cancel(String orderId){
        try{
            OrderInfo order = orderDao.queryByOrderId(orderId);
            if(order == null){
                return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"该订单不存在");
            }
            order.setStatus(6);
            order.setUpdateTime(new Date());
            order.setCancelTime(new Date());
            orderDao.save(order);
            return JsonResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.build(JsonResult.STATUS_SERVER_EXCEPTION,"系统异常");
        }
    }

    public JsonResult update(OrderDto orderDto){
        try{
            OrderInfo order = new OrderInfo();
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
    public JsonResult delete(String orderId){
        try{
            OrderInfo order = orderDao.queryByOrderId(orderId);
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
            Page<OrderInfo> page =  orderDao.findListByStatus(status,pageable);
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

package com.tp.wedding.controller;

import com.tp.wedding.common.JsonResult;
import com.tp.wedding.dto.OrderDto;
import com.tp.wedding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/add")
    public JsonResult add(OrderDto orderDto){
        return orderService.add(orderDto);
    }

    @RequestMapping("/delete")
    public JsonResult delete(String orderId){
        return orderService.delete(orderId);
    }

    @RequestMapping("/update")
    public JsonResult update(OrderDto orderDto){
        return orderService.update(orderDto);
    }

    /**
     * 支付定金
     * @return
     */
    @RequestMapping("/payDeposit")
    public JsonResult payDeposit(String orderId){
        return orderService.payDeposit(orderId);
    }

    /**
     * 支付尾款
     * @param orderId
     * @param predictSendTime
     * @return
     */
    @RequestMapping("/payBalance")
    public JsonResult payBalance(String orderId,String predictSendTime){
        return orderService.payBalance(orderId,predictSendTime);
    }

    /**
     * 发货
     * @param orderId
     * @return
     */
    @RequestMapping("/send")
    public JsonResult send(String orderId){
        return orderService.send(orderId);
    }

    /**
     * 归还
     * @param orderId
     * @return
     */
    @RequestMapping("/back")
    public JsonResult back(String orderId){
        return orderService.back(orderId);
    }

    /**
     * 完成
     * @param orderId
     * @return
     */
    @RequestMapping("/finish")
    public JsonResult finish(String orderId){
        return orderService.finish(orderId);
    }

    /**
     * 完成
     * @param orderId
     * @return
     */
    @RequestMapping("/cancel")
    public JsonResult cancel(String orderId){
        return orderService.cancel(orderId);
    }

    @RequestMapping("/findListByStatus")
    public JsonResult findListByStatus(String status,Integer pageIndex, Integer pageSize){
        return orderService.findListByStatus(status,pageIndex,pageSize);
    }

    @RequestMapping("/findOne")
    JsonResult findOne(String orderId){
        return orderService.findOne(orderId);
    }

}

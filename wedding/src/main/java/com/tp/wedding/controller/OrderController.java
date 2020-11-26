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

    @RequestMapping("/findListByStatus")
    public JsonResult findListByStatus(String status,Integer pageIndex, Integer pageSize){
        return orderService.findListByStatus(status,pageIndex,pageSize);
    }

    @RequestMapping("/findOne")
    JsonResult findOne(String orderId){
        return orderService.findOne(orderId);
    }

}

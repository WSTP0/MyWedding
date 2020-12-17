package com.tp.wedding.service;

import com.tp.wedding.listener.OrderListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ListenerManager implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    @Autowired
    private OrderService orderService;

    private ApplicationContext context;

    /**
     * 全部bean加载完执行
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.registerOrderListener();
    }

    public void registerOrderListener(){
        Map<String,OrderListener> map = context.getBeansOfType(OrderListener.class);
        List<OrderListener> orderListenerList = new ArrayList<>();
        orderListenerList.addAll(map.values());
        orderService.registerOrderListeners(orderListenerList);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

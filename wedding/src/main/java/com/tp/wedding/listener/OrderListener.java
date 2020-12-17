package com.tp.wedding.listener;

import com.tp.wedding.entity.OrderInfo;

public interface OrderListener {

    void afterOrderAdd(OrderInfo order);

    void afterOrderSend(OrderInfo order);
    void afterOrderPayDeposit(OrderInfo order);
    void afterOrderPayBalance(OrderInfo order);
    void afterOrderBack(OrderInfo order);
    void afterOrderFinish(OrderInfo order);
    void afterOrderCancel(OrderInfo order);
}

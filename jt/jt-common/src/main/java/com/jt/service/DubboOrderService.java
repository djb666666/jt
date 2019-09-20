package com.jt.service;

import com.jt.pojo.Order;

public interface DubboOrderService {

	

	Order findOrderById(String id);

	String saveOrder(Order order);



}

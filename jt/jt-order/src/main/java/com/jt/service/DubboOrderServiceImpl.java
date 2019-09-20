package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class DubboOrderServiceImpl implements DubboOrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	


	@Override
	public Order findOrderById(String id) {
		OrderShipping orderShipping = orderShippingMapper.selectById(id);
		Order order = orderMapper.selectById(id);
		QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("order_id", id);
		List<OrderItem> itemList = orderItemMapper.selectList(queryWrapper);
		order.setOrderItems(itemList)
			 .setOrderShipping(orderShipping);
		return order;
	}



	@Override
	public String saveOrder(Order order) {
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		Date date=new Date();
		order.setOrderId(orderId)
			 .setStatus(1)
			 .setCreated(date)
			 .setUpdated(date);
		orderMapper.insert(order);
		
		OrderShipping orderShipping=new OrderShipping();
		orderShipping.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		List<OrderItem> list= order.getOrderItems();
		for(OrderItem orderItem:list) {
			orderItem.setOrderId(orderId)
					 .setCreated(date)
					 .setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		order.setOrderItems(list)
			 .setOrderShipping(orderShipping);
		
		return orderId;
	}
	
	
	
	
	
	
	
	
}

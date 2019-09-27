package com.omnicuris.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.Order;
import com.omnicuris.ecommerce.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<Order> findAllOrders(){
		List<Order> productList=new ArrayList<Order>();
		orderRepository.findAll().forEach((product)->{
			productList.add(product);
		});
		return productList;
	}
	public Order findOrderDetailsWithMatchingOrderId(String orderId){
		return orderRepository.findOne(orderId);
	}
	@Override
	public boolean findOrderWithMatchingOrderId(String orderId){
		if(null!=findOrderDetailsWithMatchingOrderId(orderId))
			return true;
		return false;
	}
	
	@Override
	public Order saveOrUpdateOrder(Order order){
		return orderRepository.save(order);
	}  
	
	public void deleteOrder(String orderId){
		orderRepository.delete(orderId);
	}
	public Order findByStatus(OrderStatus status) {
		return orderRepository.findByStatus(status);
	}
	
}

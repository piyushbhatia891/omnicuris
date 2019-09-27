package com.omnicuris.ecommerce.service;

import java.util.List;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.Order;


public interface OrderService {

	List<Order> findAllOrders();
	boolean findOrderWithMatchingOrderId(String orderId);
	Order findOrderDetailsWithMatchingOrderId(String orderId);
	Order saveOrUpdateOrder(Order order);
	void deleteOrder(String orderId);
	
	Order findByStatus(OrderStatus status);
	
}

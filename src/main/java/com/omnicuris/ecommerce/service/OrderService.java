package com.omnicuris.ecommerce.service;

import java.util.List;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.OrderRestDTO;


public interface OrderService {

	List<OrderRestDTO> findAllOrders();
	boolean findOrderWithMatchingOrderId(String orderId);
	OrderRestDTO findOrderDetailsWithMatchingOrderId(String orderId);
	OrderRestDTO saveOrUpdateOrder(OrderRestDTO order);
	void deleteOrder(String orderId);
	
	OrderRestDTO findByStatus(OrderStatus status);
	
}

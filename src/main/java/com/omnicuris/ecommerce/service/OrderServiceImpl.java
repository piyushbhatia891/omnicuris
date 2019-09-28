package com.omnicuris.ecommerce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.OrderRestDTO;
import com.omnicuris.ecommerce.repository.OrderRepository;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<OrderRestDTO> findAllOrders(){
		List<OrderRestDTO> productList=new ArrayList<OrderRestDTO>();
		orderRepository.findAll().forEach((product)->{
			productList.add(product);
		});
		return productList;
	}
	public OrderRestDTO findOrderDetailsWithMatchingOrderId(String orderId){
		return orderRepository.findOne(orderId);
	}
	@Override
	public boolean findOrderWithMatchingOrderId(String orderId){
		if(null!=findOrderDetailsWithMatchingOrderId(orderId))
			return true;
		return false;
	}
	
	@Override
	public OrderRestDTO saveOrUpdateOrder(OrderRestDTO order){
		order.setDateCreated(LocalDate.now());
        return this.orderRepository.save(order);
	}  
	
	public void deleteOrder(String orderId){
		orderRepository.delete(orderId);
	}
	public OrderRestDTO findByStatus(OrderStatus status) {
		return orderRepository.findByStatus(status);
	}
	
}

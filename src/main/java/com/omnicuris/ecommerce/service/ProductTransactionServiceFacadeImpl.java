package com.omnicuris.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.exceptions.OutOfStockProductsException;
import com.omnicuris.ecommerce.models.Order;
import com.omnicuris.ecommerce.models.Product;

@Service
public class ProductTransactionServiceFacadeImpl implements ProductTransactionServiceFacade {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public ResponseEntity<Object> checkForInventoryAndRaiseAProductTransaction(Order order) {
		List<String> productIds=order.getProductIds()
				.stream()
				.map(product->{
					return product.getId().toString();
				}).collect(Collectors.toList());
		String outOfStockItems="";
		List<Product> inStockItems=new ArrayList<Product> ();
		
		 Map<String,Product> productsList =
		 productService.getProductsList(productIds);
		 
		 outOfStockItems=getOutOfStockItemIds(order, productsList);
		 
		 inStockItems = getInStockItems(order, productsList);
		 String orderId = placeAnOrder(order, inStockItems);
		 jmsTemplate.convertAndSend("OrderPlacedQueue","Order has been placed");
		 if(null!=outOfStockItems || outOfStockItems.equals("")) {
		 return new ResponseEntity<Object>(
					"Order has been created with order id as "
							+orderId,
					HttpStatus.OK);
		 }
		 else {
			 if(inStockItems.size()>0) {
			 return new ResponseEntity<Object>(
						"Order has been created with order id as "
								+orderId+" But Few Product Ids dont have stock  counts as "+ outOfStockItems,
						HttpStatus.OK);
			 }
			 else {
				 throw new OutOfStockProductsException("Order could not be created because all Product Ids dont have stock  counts as with ids as "+ outOfStockItems);
				 } 
			 }
		 
	}


	private String placeAnOrder(Order order, List<Product> inStockItems) {
		Order orderToBePlaced=new Order.ProductBuilder(order.getOrdererName(), order.getOrderSender())
		 .productIds(inStockItems)
		 .status(OrderStatus.IN_PROCESS)
		 .build();
		
		 String orderId=orderService.saveOrUpdateOrder(orderToBePlaced).getId().toString();
		return orderId;
	}


	private List<Product> getInStockItems(Order order, Map<String, Product> productsList) {
		List<Product> inStockItems;
		inStockItems=order.getProductIds().stream()		 
				 .map(product->{
					 if(productsList.get(product.getId()).getProductStockCount()
							 >product.getProductStockCount())
						 return product;
					 return null;
				 }).collect(Collectors.toList());
		return inStockItems;
	}


	private String getOutOfStockItemIds(Order order, Map<String, Product> productsList) {
		return order.getProductIds().stream()		 
		 .map(product->{
			 if(productsList.get(product.getId()).getProductStockCount()
					 <product.getProductStockCount())
				 return product.getId().toString();
			 return null;
		 }).collect(Collectors.joining(","));
	}

}
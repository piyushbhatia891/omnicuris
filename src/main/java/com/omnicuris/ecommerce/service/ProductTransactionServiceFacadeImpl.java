package com.omnicuris.ecommerce.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.exceptions.OutOfStockProductsException;
import com.omnicuris.ecommerce.models.OrderRestDTO;
import com.omnicuris.ecommerce.models.OrderProduct;
import com.omnicuris.ecommerce.models.OrderRestModel;
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
	public ResponseEntity<Object> checkForInventoryAndRaiseAProductTransaction(OrderRestModel order) {
		//Creating Order dto object
		List<OrderProduct> products=new ArrayList<OrderProduct>();
		OrderRestDTO orderToBePlaced =new OrderRestDTO();
		List<String> productIdsFromRequest = createOrderRestDTOAndProductIds(order, products, orderToBePlaced);
		
		String outOfStockItems="";
		List<Product> inStockItems=new ArrayList<Product> ();
		
		 Map<String,Product> productsListFromDB =
		 productService.getProductsList(productIdsFromRequest);

		 
		 outOfStockItems=getOutOfStockItemIds(orderToBePlaced, productsListFromDB);
		 //Getting in stock items
		 inStockItems = getInStockItems(orderToBePlaced, productsListFromDB);
		 //Getting order placed
		 String orderId = placeAnOrder(orderToBePlaced, inStockItems);
		 //Updating inventory count with respect to order
		 updatingStockBackInInventory(inStockItems, productsListFromDB);
		 
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


	private List<String> createOrderRestDTOAndProductIds(OrderRestModel order, List<OrderProduct> products,
			OrderRestDTO orderToBePlaced) {
		orderToBePlaced.setStatus(OrderStatus.IN_PROCESS);
		 orderToBePlaced.setOrderProducts(products);
		 orderToBePlaced.setDateCreated(LocalDate.now());
		List<String> productIds=order.getOrderProducts()
				.stream()
				.map(product->{
					products.add(new OrderProduct(orderToBePlaced,product,product.getProductStockCount()));
					return product.getId().toString();
				}).collect(Collectors.toList());
		return productIds;
	}


	private void updatingStockBackInInventory(List<Product> inStockItems, Map<String, Product> productsList) {
		inStockItems
		 	.stream()
		 	.forEach(product->{
		 		Product productToBeUpdatedInDb=productsList.get(product.getId());
		 		productToBeUpdatedInDb
		 		.setProductStockCount(productToBeUpdatedInDb.getProductStockCount()-product.getProductStockCount());
		 		productService.saveOrUpdateProduct(productToBeUpdatedInDb);
		 	});
	}


	private String placeAnOrder(OrderRestDTO orderToBePlaced, List<Product> inStockItems) {
		OrderRestDTO order =new OrderRestDTO();
		List<OrderProduct> products=new ArrayList<OrderProduct>();
		order.setStatus(OrderStatus.IN_PROCESS);
		order.setOrderProducts(products);
		order.setDateCreated(LocalDate.now());
		inStockItems.stream()
			.forEach((product)->{
				products.add(new OrderProduct(order,product,product.getProductStockCount()));
			});
		
		
		String orderId=orderService.saveOrUpdateOrder(order).getId().toString();
		return orderId;
	}


	private List<Product> getInStockItems(OrderRestDTO order, Map<String, Product> productsList) {
		return order.getOrderProducts().stream()		 
				 .map(product->{
					 if(productsList.get(product.getProduct().getId()).getProductStockCount()
							 > product.getProduct().getProductStockCount())
						 return product.getProduct();
					 return null;
				 }).collect(Collectors.toList());
		
	}


	private String getOutOfStockItemIds(OrderRestDTO order, Map<String, Product> productsList) {
		return order.getOrderProducts().stream()		 
		 .map(product->{
			 if(productsList.get(product.getProduct().getId()).getProductStockCount()
					 < product.getProduct().getProductStockCount())
				 return product.getProduct().getId().toString();
			 return null;
		 }).collect(Collectors.joining(","));
	}

}
package com.omnicuris.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.omnicuris.ecommerce.models.Order;
import com.omnicuris.ecommerce.service.OrderService;
import com.omnicuris.ecommerce.service.ProductTransactionServiceFacade;

@RestController
@ResponseBody
@Validated
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	ProductTransactionServiceFacade orderFacadeService;

	/*
	 * 
	 * Api call for getting orders list
	 */
    @RequestMapping("/orders") 
    public ResponseEntity<Object> getOrders(){    	
        List<Order> orders=orderService.findAllOrders();
        return new ResponseEntity<Object>(orders, HttpStatus.OK);   
    }
    
	/*
	 * Api call for making single or bulk order
	 */
    @RequestMapping(value = "/orders/new",method = RequestMethod.POST)
    public ResponseEntity<Object> createNewOrder(@RequestBody @Valid Order order){
    	return orderFacadeService.checkForInventoryAndRaiseAProductTransaction(order);
    }
    
}

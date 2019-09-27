package com.omnicuris.ecommerce.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.omnicuris.ecommerce.models.Order;

public interface ProductTransactionServiceFacade {

	ResponseEntity<Object> checkForInventoryAndRaiseAProductTransaction(Order order);
}

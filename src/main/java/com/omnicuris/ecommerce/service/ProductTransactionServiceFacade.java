package com.omnicuris.ecommerce.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.omnicuris.ecommerce.models.OrderRestDTO;
import com.omnicuris.ecommerce.models.OrderRestModel;

public interface ProductTransactionServiceFacade {

	ResponseEntity<Object> checkForInventoryAndRaiseAProductTransaction(OrderRestModel order);
}

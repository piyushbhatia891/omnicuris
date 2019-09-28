package com.omnicuris.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.OrderRestDTO;

@Repository
public interface OrderRepository extends CrudRepository<OrderRestDTO,String> {
		OrderRestDTO findByStatus(OrderStatus status);
}

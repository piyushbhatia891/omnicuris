package com.omnicuris.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order,String> {
		Order findByStatus(OrderStatus status);
}

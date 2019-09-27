package com.omnicuris.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,String> {

	Product findByProductName(String productName);
}

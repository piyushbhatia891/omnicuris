package com.omnicuris.ecommerce.service;

import java.util.List;
import java.util.Map;

import org.glassfish.hk2.api.Visibility;

import com.omnicuris.ecommerce.models.Product;


public interface ProductService {

	List<Product> findAllProducts();
	boolean findProductWithMatchingProductId(String productId);
	Product findProductDetailsWithMatchingProductId(String productId);
	Product saveOrUpdateProduct(Product product);
	void deleteProduct(String productId);
	Map<String,Product> getProductsList(List<String> productIds);
	
	Product findByProductName(String name);
	
}

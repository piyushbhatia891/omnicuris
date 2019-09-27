package com.omnicuris.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.glassfish.hk2.api.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omnicuris.ecommerce.models.Product;
import com.omnicuris.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> findAllProducts(){
		List<Product> productList=new ArrayList<Product>();
		productRepository.findAll().forEach((product)->{
			productList.add(product);
		});
		return productList;
	}
	public Product findProductDetailsWithMatchingProductId(String productId){
		return productRepository.findOne(productId);
	}
	@Override
	public boolean findProductWithMatchingProductId(String  productId){
		if(null!=findProductDetailsWithMatchingProductId(productId))
			return true;
		return false;
	}
	public Product saveOrUpdateProduct(Product product){
		return productRepository.save(product);
	}  
	
	public void deleteProduct(String productId){
		productRepository.delete(productId);
	} 

	public Map<String,Product> getProductsList(List<String> productIds) {
		return productIds.stream()
				.map(productId->{
					return findProductDetailsWithMatchingProductId(productId);
				}).collect(Collectors.toMap(Product::getId, product->product));
	}
	
	public Product findByProductName(String name) {
		return productRepository.findByProductName(name);
	}
	
}

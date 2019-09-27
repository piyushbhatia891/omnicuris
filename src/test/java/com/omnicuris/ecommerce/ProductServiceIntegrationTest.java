package com.omnicuris.ecommerce;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.omnicuris.ecommerce.models.Product;
import com.omnicuris.ecommerce.repository.ProductRepository;
import com.omnicuris.ecommerce.service.ProductService;
import com.omnicuris.ecommerce.service.ProductServiceImpl;

public class ProductServiceIntegrationTest {

	@TestConfiguration
    static class ProductServiceImplTestContextConfiguration {
  
        @Bean
        public ProductService productService() {
            return new ProductServiceImpl();
        }
    }
 
    @Autowired
    private ProductService productService;
 
    @MockBean
    private ProductRepository productRepository;
    @org.junit.Before
    public void setUp() {
    	Product alex = new Product.ProductBuilder("alex","hales")
    			
        		.brandName("nike")
        		.productSellingPrice(100)
        		.productSku("123")
        		.productStockCount(100)
        		.build();
     
        Mockito.when(productRepository.findByProductName(alex.getProductName()))
          .thenReturn(alex);
    }
    
    @Test
    public void validProductBeFound() {
        String name = "alex";
        Product found = productService.findByProductName(name);      
         assertThat(found.getName())
          .isEqualTo(name);
     }
}

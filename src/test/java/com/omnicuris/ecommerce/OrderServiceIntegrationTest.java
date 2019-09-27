package com.omnicuris.ecommerce;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.Order;
import com.omnicuris.ecommerce.models.Product;
import com.omnicuris.ecommerce.repository.OrderRepository;
import com.omnicuris.ecommerce.repository.ProductRepository;
import com.omnicuris.ecommerce.service.OrderService;
import com.omnicuris.ecommerce.service.OrderServiceImpl;
import com.omnicuris.ecommerce.service.ProductService;
import com.omnicuris.ecommerce.service.ProductServiceImpl;

public class OrderServiceIntegrationTest {

	@TestConfiguration
    static class OrderServiceImplTestContextConfiguration {
  
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
    }
 
    @Autowired
    private OrderService orderService;
 
    @MockBean
    private OrderRepository orderRepository;
    @org.junit.Before
    public void setUp() {
    	Order order = new Order.ProductBuilder("alex","hales")
        		.status(OrderStatus.IN_PROCESS)
        		.build();
     
        Mockito.when(orderRepository.findByStatus(OrderStatus.IN_PROCESS))
          .thenReturn(order);
    }
    
    @Test
    public void validProductBeFound() {
        String ordererName = "alex";
        Order found = orderService.findByStatus(OrderStatus.IN_PROCESS);      
         assertThat(found.getOrdererName())
          .isEqualTo(ordererName);
     }
}

package com.omnicuris.ecommerce;

import static org.junit.Assert.assertThat;

import javax.annotation.sql.DataSourceDefinitions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.omnicuris.ecommerce.enums.OrderStatus;
import com.omnicuris.ecommerce.models.Order;
import com.omnicuris.ecommerce.models.Product;
import com.omnicuris.ecommerce.repository.OrderRepository;
import com.omnicuris.ecommerce.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@WebAppConfiguration
public abstract class OrderApiTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private OrderRepository orderRepository;
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Order order = new Order.ProductBuilder("alex","hales")
        		.status(OrderStatus.IN_PROCESS)
        		.build();
        entityManager.persist(order);
        entityManager.flush();
     
        // when
        Order found = orderRepository.findByStatus(order.getStatus());
     
        // then
        assertThat(found.getId(),
          equals(order.getId()));
    }
}
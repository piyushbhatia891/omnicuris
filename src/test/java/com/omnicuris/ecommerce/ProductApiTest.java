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

import com.omnicuris.ecommerce.models.Product;
import com.omnicuris.ecommerce.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@WebAppConfiguration
public abstract class ProductApiTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Product alex = new Product.ProductBuilder("alex","hales")
        		.brandName("nike")
        		.productSellingPrice(100)
        		.productSku("123")
        		.productStockCount(100)
        		.build();
        entityManager.persist(alex);
        entityManager.flush();
     
        // when
        Product found = productRepository.findByProductName(alex.getProductName());
     
        // then
        assertThat(found.getProductName(),
          equals(alex.getProductName()));
    }
}
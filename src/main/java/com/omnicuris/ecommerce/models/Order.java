package com.omnicuris.ecommerce.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import com.omnicuris.ecommerce.enums.OrderStatus;

@Entity
public class Order{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
    private List<Product> productIds;
    
    private OrderStatus status;
    @NotNull(message = "Orderer name must not be empty")    
    private String ordererName;
    @NotNull(message = "Sender name must not be empty")
    private String orderSender;
    

	public Order() {
		
    }
    private  Order(ProductBuilder productBuilder) {
    	this.productIds=productBuilder.productIds;
    	this.status=productBuilder.status;
    	this.ordererName=productBuilder.ordererName;
    	this.orderSender=productBuilder.orderSender;
	}


    public String getId() {
		return id;
	}
	public List<Product> getProductIds() {
		return productIds;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public String getOrdererName() {
		return ordererName;
	}
	public String getOrderSender() {
		return orderSender;
	}
	@Override
    public String toString() {
        return "";
    }
    public static class ProductBuilder{
    	private List<Product> productIds;
        private OrderStatus status;
        private String ordererName;
        private String orderSender;

        public ProductBuilder(String ordererName, String orderSender) {
            this.ordererName=ordererName;
            this.orderSender=orderSender;
        }
        
        public ProductBuilder status(OrderStatus status){
            this.status=status;
            return this;
        }
        
        public ProductBuilder productIds(List<Product> productIds){
            this.productIds=productIds;
            return this;
        }
        
        public Order build() {
            Order product =  new Order(this);
            return product;
        }

    }
        
}

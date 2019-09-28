package com.omnicuris.ecommerce.models;

import java.awt.List;
import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderProduct {
 
    @EmbeddedId
    @JsonIgnore
    private OrderProductPk orderKey;
 
    @Column(nullable = false)
    private Integer quantity;
 
    // default constructor
 
    public OrderProduct(OrderRestDTO order, Product product, Integer quantity) {
    	orderKey = new OrderProductPk();
    	orderKey.setOrder(order);
    	orderKey.setProduct(product);
        this.quantity = quantity;
    }
 
    @Transient
    public Product getProduct() {
        return this.orderKey.getProduct();
    }
 
    @Transient
    public Double getTotalPrice() {
        return getProduct().getProductSellingPrice() * getQuantity();
    }

		public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
 

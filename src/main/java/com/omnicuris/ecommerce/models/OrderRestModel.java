package com.omnicuris.ecommerce.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderRestModel extends AuditModel{
 
    private List<Product> orderProducts = new ArrayList<>();
 
    public List<Product> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
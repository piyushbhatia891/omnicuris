package com.omnicuris.ecommerce.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

@Entity
public class Product extends AuditModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
 
    private Double productSellingPrice;
 
    @NotNull(message = "Product name must not be empty")
    private String productName;
    
    private int productStockCount;

    public void setId(String id) {
		this.id = id;
	}

	public Double getProductSellingPrice() {
		return productSellingPrice;
	}

	public Product() {
    }
	public void setProductStockCount(int productStockCount) {
		this.productStockCount = productStockCount;
	}



	private  Product(ProductBuilder productBuilder) {
    	this.productName = productBuilder.productName;
        this.productStockCount = productBuilder.productStockCount;
        this.productSellingPrice = productBuilder.productSellingPrice;
	}

    public String getId() {
		return id;
	}

   public String getProductName() {
        return productName;
    }

    
    public int getProductStockCount() {
        return productStockCount;
    }

    
    @Override
    public String toString() {
        return "";
    }
    public static class ProductBuilder{
        private final String productName;
        private int productStockCount;
        private Double productSellingPrice;

        public ProductBuilder(String productName) {
            this.productName = productName;
        }
        
        public ProductBuilder productStockCount(int productStockCount){
            this.productStockCount=productStockCount;
            return this;
        }
        public ProductBuilder productSellingPrice(Double productSellingPrice){
            this.productSellingPrice=productSellingPrice;
            return this;
        }
        public Product build() {
            Product product =  new Product(this);
            return product;
        }

    }
        
}

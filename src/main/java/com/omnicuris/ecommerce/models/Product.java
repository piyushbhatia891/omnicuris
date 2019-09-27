package com.omnicuris.ecommerce.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

@Entity
public class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
    @NotNull(message = "Product name must not be empty")
    private String productName;
    
    private String productDescription;
    private String brandName;
    @NotNull(message = "Product sku must not be empty")
    private String productSku;
    private String productImageUrl;
    private int productStockCount;
    private int productSellingPrice;

    public void setId(String id) {
		this.id = id;
	}



	public Product() {
    }
    
   

	public void setProductStockCount(int productStockCount) {
		this.productStockCount = productStockCount;
	}



	private  Product(ProductBuilder productBuilder) {
    	this.productName = productBuilder.productName;
        this.productDescription = productBuilder.productDescription;
        this.brandName = productBuilder.brandName;
        this.productSku = productBuilder.productSku;
        this.productImageUrl = productBuilder.productImageUrl;
        this.productStockCount = productBuilder.productStockCount;
        this.productSellingPrice = productBuilder.productSellingPrice;
	}

    public String getId() {
		return id;
	}

   public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductSku() {
        return productSku;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public int getProductStockCount() {
        return productStockCount;
    }

    public int getProductSellingPrice() {
        return productSellingPrice;
    }

    @Override
    public String toString() {
        return "";
    }
    public static class ProductBuilder{
        private final String productName;
        private final String productDescription;
        private String brandName;
        private String productSku;
        private String productImageUrl;
        private int productStockCount;
        private int productSellingPrice;

        public ProductBuilder(String productName, String productDescription) {
            this.productName = productName;
            this.productDescription = productDescription;
        }
        public ProductBuilder brandName(String brandName){
            this.brandName=brandName;
            return this;
        }
        public ProductBuilder productSku(String productSku){
            this.productSku=productSku;
            return this;
        }

        public ProductBuilder productImageUrl(String productImageUrl){
            this.productImageUrl=productImageUrl;
            return this;
        }

        public ProductBuilder productStockCount(int productStockCount){
            this.productStockCount=productStockCount;
            return this;
        }
        public ProductBuilder productSellingPrice(int productSellingPrice){
            this.productSellingPrice=productSellingPrice;
            return this;
        }
        public Product build() {
            Product product =  new Product(this);
            return product;
        }

    }
        
}

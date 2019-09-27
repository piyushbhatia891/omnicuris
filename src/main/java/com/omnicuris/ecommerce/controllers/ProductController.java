package com.omnicuris.ecommerce.controllers;

import com.omnicuris.ecommerce.exceptions.ProductDoesNotExistException;
import com.omnicuris.ecommerce.models.Product;
import com.omnicuris.ecommerce.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@RestController
@ResponseBody
@Validated
public class ProductController {
	@Autowired
	private ProductService productService;
	
    @RequestMapping("/products") 
    public ResponseEntity<Object> getProducts(){    	
        List<Product> products=productService.findAllProducts();
        return new ResponseEntity<Object>(products, HttpStatus.OK);   
    }
    /*
     * Api call for creating new product
     */
    @RequestMapping(value = "/products/new",method = RequestMethod.POST)
    public ResponseEntity<Object> insertNewProduct(@RequestBody @Valid Product product){
    		String id=productService.saveOrUpdateProduct(product).getId();
    		return new ResponseEntity<Object>("Product is successfully created with new product id as "+id,HttpStatus.CREATED);
    }

    /*
     * Api call for updating existing product
     */
    @RequestMapping(value = "/products/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateExistingProduct(@PathVariable("id") String id,@RequestBody Product product){
    	if(productService.findProductWithMatchingProductId(id)){
    		productService.saveOrUpdateProduct(product);
        	return new ResponseEntity<Object>("Product is successfully updated with new product id as "+product.getId(),HttpStatus.CREATED);
        }
    	throw new ProductDoesNotExistException("Product with id as "+ id+ " does not exist.Please resubmit the request");
    }
    
    /*
     * Api call for deleting existing product
     */
    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteExistingProduct(@PathVariable("id") String id){
    	if(productService.findProductWithMatchingProductId(id)){
    		productService.deleteProduct(id);
            return new ResponseEntity<Object>("Product is successfully deleted",HttpStatus.OK);    		
        } 
    	throw new ProductDoesNotExistException("Product with id as "+ id+ " does not exist.Please resubmit the request");    }

}

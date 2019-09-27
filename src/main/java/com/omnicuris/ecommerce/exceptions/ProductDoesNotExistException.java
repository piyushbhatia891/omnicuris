package com.omnicuris.ecommerce.exceptions;

public class ProductDoesNotExistException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductDoesNotExistException(String message) {
        super(message);
    }
}
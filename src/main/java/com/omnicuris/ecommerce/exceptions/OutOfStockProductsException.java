package com.omnicuris.ecommerce.exceptions;

public class OutOfStockProductsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfStockProductsException(String message) {
        super(message);
    }
}
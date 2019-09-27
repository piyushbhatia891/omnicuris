package com.omnicuris.ecommerce.exceptions;

public class RestApiException extends RuntimeException {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public RestApiException(String message, String code) {
        super(message);
       
    }
    public RestApiException(String message) {
        super(message);        
    }
}
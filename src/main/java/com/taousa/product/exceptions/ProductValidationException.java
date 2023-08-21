package com.taousa.product.exceptions;

public class ProductValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductValidationException() {
		super();
	}
	
	public ProductValidationException(String name) {
		super(name);
	}

}

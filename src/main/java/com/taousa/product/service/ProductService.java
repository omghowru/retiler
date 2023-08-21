package com.taousa.product.service;

import java.sql.Date;
import java.util.List;

import com.taousa.product.exceptions.ProductValidationException;
import com.taousa.product.model.Product;

public interface ProductService {

	
	List<Product> getActiveProducts();
	List<Product> getProductsBasedOnSearch(String name, String minprice, String maxprice, Date minPostedDate, Date maxPostedDate);
	boolean createProduct(Product product) throws ProductValidationException;
	boolean updateProduct(Long id,Product product);
	boolean removeProduct(Long productId) throws Exception;
	
	
}

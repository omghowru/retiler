package com.taousa.product.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taousa.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByStatus(String status);
	List<Product> findAllByDateBetween(Date publicationTimeStart, Date publicationTimeEnd);
	List<Product> findByName(String name);
	List<Product> findAllByPriceBetween(int maxprice, int minprice);
      

}

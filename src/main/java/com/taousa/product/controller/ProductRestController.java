
package com.taousa.product.controller;

import java.sql.Date;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taousa.product.exceptions.ProductValidationException;
import com.taousa.product.model.Product;
import com.taousa.product.model.Response;
import com.taousa.product.service.AprovalService;
import com.taousa.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

	@Autowired()
	ProductService service;

	@Autowired
	AprovalService aprovalService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProducts() {

		if (service.getActiveProducts() == null) {
			return new ResponseEntity<>("No Active Products..", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.getActiveProducts(), HttpStatus.OK);
	}

	@GetMapping(value="/search",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchProducts(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "minprice", required = false) String minprice,
			@RequestParam(name = "maxprice", required = false) String maxprice,
			@RequestParam(name = "minPostedDate", required = false) Date minPostedDate,
			@RequestParam(name = "maxPostedDate", required = false) Date maxPostedDate) throws ParseException {

		if (!((service.getProductsBasedOnSearch(name, minprice, maxprice, minPostedDate, maxPostedDate)) == null)) {
			return new ResponseEntity<>(
					service.getProductsBasedOnSearch(name, minprice, maxprice, minPostedDate, maxPostedDate),
					HttpStatus.ACCEPTED);

		}

		return new ResponseEntity<>("Please Give Valid Values..", HttpStatus.BAD_REQUEST);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProduct(@RequestBody Product product)throws ProductValidationException {
		if(service.createProduct(product)) {
			System.out.println("product.."+product);
		return new ResponseEntity<>(new Response("sucessfully created...",HttpStatus.OK.value()), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(new Response("Not added...",HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	
		}}

	@PutMapping(value="/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProduct(@PathVariable(value = "productId") Long productId, @RequestBody Product product) {
		if(service.updateProduct(productId, product)) {

		return new ResponseEntity<>(new Response("sucessfully updated...",HttpStatus.OK.value()), HttpStatus.OK);
	
		}else {
			return new ResponseEntity<>(new Response("Not Updated...",HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	
		}
		}

	@DeleteMapping(value="/{productId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeProduct(@PathVariable(value = "productId") Long productId) throws Exception {
		if(service.removeProduct(productId)) {
	
		return new ResponseEntity<>(new Response("sucessfully removed...",HttpStatus.OK.value()), HttpStatus.OK);
	}else {
		return new ResponseEntity<>(new Response("Please provide valid productid...",HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

	}
	}

	@GetMapping(value="/approval-queue",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getApprovalQueue() {
		if (aprovalService.getAllProducts() == null) {
			return new ResponseEntity<>(new Response("No active products...",HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(aprovalService.getAllProducts(), HttpStatus.OK);

	}

	@PutMapping(value="approval-queue/{approvalId}/approve",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> giveApproval(@PathVariable(value = "approvalId") Long approvalId) {
		if(aprovalService.updateStatusApproved(approvalId)) {
		return new ResponseEntity<>(new Response("sucessfully approved...",HttpStatus.OK.value()),HttpStatus.OK);

	}else {
		return new ResponseEntity<>(new Response("Please provide valid approvalId.....",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);

	}
	}
	@PutMapping(value="/approval-queue/{approvalId}/reject",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rejectProduct(@PathVariable(value = "approvalId") Long approvalId) {
		if(aprovalService.rejectTheProduct(approvalId)) {
        
		return new ResponseEntity<>(new Response("sucessfully rejected...",HttpStatus.OK.value()),HttpStatus.OK);
		
        }else {
    		return new ResponseEntity<>(new Response("Please provide valid approvalId.....",HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
	}
	}
}
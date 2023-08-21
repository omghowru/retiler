package com.taousa.product.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taousa.product.exceptions.ProductValidationException;
import com.taousa.product.model.ApprovalQueue;
import com.taousa.product.model.Product;
import com.taousa.product.repository.ApprovalQueueRepository;
import com.taousa.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository repo;
	@Autowired
	ApprovalQueueRepository approvalRepo;

	private ProductServiceImpl() {

	}

	@Override
	public List<Product> getActiveProducts() {
		// TODO Auto-generated method stub

		return repo.findAll(Sort.by(Sort.Direction.DESC, "date")).stream()
				.filter(p -> p.getStatus().equalsIgnoreCase("active")).toList();

	}

	@Override
	public List<Product> getProductsBasedOnSearch(String name, String minPrice, String maxprice, Date minPostedDate,
			Date maxPostedDate) {
		// TODO Auto-generated method stub
		if (name != null) {
			return repo.findByName(name);

		}
		if ((minPrice != null) && (maxprice != null)) {

			return repo.findAllByPriceBetween(Integer.parseInt(minPrice), Integer.parseInt(maxprice));
		}
		if ((minPostedDate!=null )&& (maxPostedDate!=null)) {
			repo.findAllByDateBetween(minPostedDate, maxPostedDate);
		}
		return null;
	}

	@Override
	public boolean createProduct(Product product)throws ProductValidationException {
		// TODO Auto-generated method stub
		//validations for product
		validations(product);
		if (product.getPrice() <= 10000) {
			product.setDate(product.getDate());
			product.setStatus("Active");
			repo.save(product);
			return true;
		} else {
			ApprovalQueue queue = new ApprovalQueue();
			queue.setDate(product.getDate());
			queue.setName(product.getName());
			queue.setPrice(product.getPrice());
			queue.setStatus("Awaiting approval");
			approvalRepo.save(queue);
			return true;

		}
	
	}

	@Override
	public boolean updateProduct(Long id,Product product) {
		// TODO Auto-generated method stub
         Optional<Product> value =  repo.findById(id);
        if( value.isPresent()){
           Product single=  value.get();
           validations(product);
           
          if( !caluculation(single.getPrice(),product.getPrice())){
        		ApprovalQueue queue = new ApprovalQueue();
    			queue.setDate(product.getDate());
    			queue.setName(product.getName());
    			queue.setPrice(product.getPrice());
    			queue.setStatus("Awaiting approval");
    			approvalRepo.save(queue);
    			repo.deleteById(id);
      			return true;

          }else {
  			repo.save(product);
  			return true;

          }
         }
		return false;
        
              
             
	}

	@Override
	public  boolean removeProduct(Long productId) throws Exception {
		// TODO Auto-generated method stub
		  Optional<Product> value =  repo.findById(productId);
	        if( value.isPresent()){
	           Product product=  value.get();
	           repo.deleteById(productId);
	           ApprovalQueue queue = new ApprovalQueue();
   			queue.setDate(product.getDate());
   			queue.setName(product.getName());
   			queue.setPrice(product.getPrice());
   			queue.setStatus("Awaiting approval");
   			approvalRepo.save(queue);
   			return true;
	        }else {
	        	return false;
 	        }
	}
	
	public boolean caluculation(int priviousprice,int currentprice) {
		priviousprice=100; 
		currentprice=200;
		int k=(int)(priviousprice*(50.0f/100.0f));
		if((k+priviousprice) <currentprice) {
		return true;
		}
		return false;
	}

	
	public void validations(Product product) throws ProductValidationException{
	
		if(product.getName()==null ||product.getName().isEmpty()) {
			throw new ProductValidationException("Please provide valid product name. ");
		}
		if(product.getDate()==null) {
			throw new ProductValidationException("Please provide valid product Date..");

		}
		if(product.getPrice()<0) {
			throw new ProductValidationException("Please provide product price..");

		}
	}
	
}

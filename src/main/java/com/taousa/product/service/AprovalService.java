package com.taousa.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.taousa.product.exceptions.ProductValidationException;
import com.taousa.product.model.ApprovalQueue;
import com.taousa.product.repository.ApprovalQueueRepository;
@Service
public class AprovalService implements ApprovalService{

	@Autowired
	ApprovalQueueRepository repo;
	

	@Override
	public List<ApprovalQueue> getAllProducts() {
		// TODO Auto-generated method stub
		return repo.findAll(Sort.by(Sort.Direction.DESC, "date"));
	}

	@Override
	public boolean updateStatusApproved(Long approvalid) {
		// TODO Auto-generated method stub
	Optional<ApprovalQueue>	value=repo.findById(approvalid);
	   if(value.isPresent()) {
		   ApprovalQueue aprovalQueue=  value.get();
		   aprovalQueue.setStatus("Approved");
		   repo.deleteById(approvalid);
		   return true;
	   }
	return false;
		
	}

	@Override
	public boolean rejectTheProduct(Long approvalId) {
		// TODO Auto-generated method stub
		Optional<ApprovalQueue>	value=repo.findById(approvalId);
		if(value.isPresent()) {
			   repo.deleteById(approvalId);
			   return true;
		   }
		else {
			return false;
		}

	}

}

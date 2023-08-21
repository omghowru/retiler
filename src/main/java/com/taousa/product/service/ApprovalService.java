package com.taousa.product.service;

import java.util.List;

import com.taousa.product.model.ApprovalQueue;

public interface ApprovalService {

	
	List<ApprovalQueue> getAllProducts();
	boolean updateStatusApproved(Long approvalid);
	boolean rejectTheProduct(Long approvalId);
}

package com.taousa.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taousa.product.model.ApprovalQueue;

public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue, Long>{

}

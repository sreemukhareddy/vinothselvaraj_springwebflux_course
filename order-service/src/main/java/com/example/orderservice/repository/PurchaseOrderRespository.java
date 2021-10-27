package com.example.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderservice.entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRespository extends JpaRepository<PurchaseOrder, Integer>{

	public List<PurchaseOrder> findByUserId(Integer userId);
}

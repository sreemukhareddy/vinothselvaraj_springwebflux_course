package com.example.orderservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.orderservice.dto.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PurchaseOrder {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String productId;
	private Integer userId;
	private Integer amount;
	private OrderStatus status;
}

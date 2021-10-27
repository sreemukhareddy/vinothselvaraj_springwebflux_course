package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseOrderResponseDto {
	private Integer userId;
	private String productId;
	private Integer orderId;
	private Integer amount;
	private OrderStatus orderStatus;
}

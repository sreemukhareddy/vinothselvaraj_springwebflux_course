package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestContext {
	
	private PurchaseOrderRequestDto purchaseOrderRequestDto;
	private ProductDTO productDTO;
	private TransactionRequestDto transactionRequestDto;
	private TransactionResponseDto transactionResponseDto;

	public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
		this.purchaseOrderRequestDto = purchaseOrderRequestDto;
	}
}

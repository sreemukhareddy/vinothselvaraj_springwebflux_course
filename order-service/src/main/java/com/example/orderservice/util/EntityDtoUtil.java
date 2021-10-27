package com.example.orderservice.util;

import org.springframework.beans.BeanUtils;

import com.example.orderservice.dto.OrderStatus;
import com.example.orderservice.dto.PurchaseOrderResponseDto;
import com.example.orderservice.dto.RequestContext;
import com.example.orderservice.dto.TransactionRequestDto;
import com.example.orderservice.dto.TransactionStatus;
import com.example.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {
	
	public static void setTransactionRequest(RequestContext requestContext) {
		TransactionRequestDto transactionRequestDto = TransactionRequestDto.builder()
				                                                           .userId(requestContext.getPurchaseOrderRequestDto().getUserId())
				                                                           .amount(requestContext.getProductDTO().getPrice())
				                                                           .build();
		requestContext.setTransactionRequestDto(transactionRequestDto);
	}
	
	public static PurchaseOrder getPurchaseOrder(RequestContext requestContext) {
		PurchaseOrder purchaseOrder = PurchaseOrder.builder()
		             .userId(requestContext.getPurchaseOrderRequestDto().getUserId())
		             .productId(requestContext.getPurchaseOrderRequestDto().getProductId())
		             .amount(requestContext.getProductDTO().getPrice())
		             .build();
		
		TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
		
		OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
		
		purchaseOrder.setStatus(orderStatus);
		return purchaseOrder;
	}
	
	public static PurchaseOrderResponseDto getPurchaseOrderRespopnseDto(PurchaseOrder purchaseOrder) {
		PurchaseOrderResponseDto purchaseOrderResponseDto = new PurchaseOrderResponseDto();
		BeanUtils.copyProperties(purchaseOrder, purchaseOrderResponseDto);
		purchaseOrderResponseDto.setOrderId(purchaseOrder.getId());
		purchaseOrderResponseDto.setOrderStatus(purchaseOrder.getStatus());
		return purchaseOrderResponseDto;																		
	}

}

package com.example.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.ProductDTO;
import com.example.orderservice.dto.PurchaseOrderRequestDto;
import com.example.orderservice.dto.PurchaseOrderResponseDto;
import com.example.orderservice.dto.RequestContext;
import com.example.orderservice.dto.UserDto;
import com.example.orderservice.repository.PurchaseOrderRespository;
import com.example.orderservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderFulfillmentService {

	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private PurchaseOrderRespository purchaseOrderRespository;
	
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> purchaseOrderReqDtoMono) {
					return purchaseOrderReqDtoMono
							.map(RequestContext::new)
							.flatMap(this::productRequestResponse)
							.doOnNext(EntityDtoUtil::setTransactionRequest)
							.flatMap(this::userRequestResponse)
							.map(EntityDtoUtil::getPurchaseOrder)
							.map(this.purchaseOrderRespository::save)
							.map(EntityDtoUtil::getPurchaseOrderRespopnseDto)
							.subscribeOn(Schedulers.boundedElastic())
							;
							
	}
	
	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
		                  .doOnNext(rc::setProductDTO)
		                  .thenReturn(rc);
	}
	
	private Mono<RequestContext> userRequestResponse(RequestContext rc) {
		return this.userClient.authTransaction(rc.getTransactionRequestDto())
				       .doOnNext(trdto -> {
				    	   System.out.println(trdto);
				       })
		               .doOnNext(rc::setTransactionResponseDto)
		               .thenReturn(rc);
	}
	
	public Flux<PurchaseOrderResponseDto> placeBulkOrders() {
		
		return Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
		    .flatMap(tuple -> {
		    	UserDto user = tuple.getT1();
		    	
		    	ProductDTO product = tuple.getT2();
		    	
		    	if(user == null || product == null)
		    		return Flux.empty();
		    	
		    	PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
		    	dto.setUserId(user.getId());
		    	dto.setProductId(product.getId());
		    	Mono<PurchaseOrderRequestDto> purchaseOrderReqDtoMono = Mono.fromCallable(() -> dto);
		    	return processOrder(purchaseOrderReqDtoMono);
		    });
		    
		    
		    
	}
	
}

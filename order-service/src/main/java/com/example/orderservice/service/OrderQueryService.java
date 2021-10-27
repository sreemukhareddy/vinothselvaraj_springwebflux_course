package com.example.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.orderservice.dto.PurchaseOrderResponseDto;
import com.example.orderservice.repository.PurchaseOrderRespository;
import com.example.orderservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

	@Autowired
	private PurchaseOrderRespository purchaseOrderRespository;

	public Flux<PurchaseOrderResponseDto> getProductsByUSerId(int userId) {
		return Flux.fromStream(() -> this.purchaseOrderRespository.findByUserId(userId).stream())
				.subscribeOn(Schedulers.boundedElastic())
				.map(EntityDtoUtil::getPurchaseOrderRespopnseDto);
	}

}

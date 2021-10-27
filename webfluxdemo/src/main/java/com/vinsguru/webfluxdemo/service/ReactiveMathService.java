package com.vinsguru.webfluxdemo.service;

import java.time.Duration;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinsguru.webfluxdemo.Util;
import com.vinsguru.webfluxdemo.dto.MultiplyReqDto;
import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.exception.InputValidationException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveMathService {

	public Mono<Response> findsquare(int num) {
		return Mono.fromSupplier(() -> {
			return num * num;
		}).map(Response::new);
	}
	
	public Mono<Response> multiply(Mono<MultiplyReqDto> reqDto) {
		// 1st way
		/*
		return reqDto
				.map(req -> {
					if (req.getFirst() > 10 || req.getSecond() < 10) {
						InputValidationException exc = new InputValidationException(0);
						throw exc;
					}
					return req;
				})
				.map(req ->req.getFirst() * req.getSecond())
					.map(Response::new);
		*/
		// 2nd way
		return reqDto
				.handle((req, sink) -> {
					if (req.getFirst() > 10 || req.getSecond() < 10) {
						InputValidationException exc = new InputValidationException(0);
						sink.error(exc);
					} else {
						sink.next(req);
					}
					
				})
				.cast(MultiplyReqDto.class)
				.map(req ->req.getFirst() * req.getSecond())
					.map(Response::new);
	}

	public Flux<Response> table(int num) {
		System.out.println("called Thread is " + Thread.currentThread().getName());
		return Flux.range(1, 10)
				.doOnNext(i -> System.out.println("Near range Thread is " + Thread.currentThread().getName()))
				.doOnNext(i -> Util.sleep(2))
				.subscribeOn(Schedulers.boundedElastic())
				.doOnNext(i -> System.out.println("Current Thread is " + Thread.currentThread().getName()))
				.map(i -> i * num)
				.map(Response::new);
	}
	
	public Mono<ResponseEntity<Response>> multiplicationAssignment(int num) {
		System.out.println("called Thread is " + Thread.currentThread().getName());
		return Mono.just(num)
				.filter(i -> i > 10 && i < 20)
				.flatMap(this::findsquare)
				.cast(Response.class)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
				
				
	}

}

package com.vinsguru.webfluxdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vinsguru.webfluxdemo.dto.MultiplyReqDto;
import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.exception.InputValidationException;
import com.vinsguru.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
	
	@Autowired
	private ReactiveMathService mathService;
	
	public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		
		// 1st way
		return ServerResponse.ok().body(mathService.findsquare(input), Response.class);
		
		// 2nd way
		/*
		return mathService
					.findsquare(input)
					.flatMap(res -> ServerResponse.ok().bodyValue(res));
		*/
	}
	
	public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		if(input > 10) {
			return Mono.error(new InputValidationException(input));
		}
		//return ServerResponse.ok().body(mathService.findsquare(input), Response.class);
		// 2nd way 
		return mathService
					.findsquare(input)
					.flatMap(res -> ServerResponse.ok().bodyValue(res));
		
	}
	
	public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> table = mathService.table(input);
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(table, Response.class);
	}
	
	public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
		Mono<MultiplyReqDto> reqDto = serverRequest.bodyToMono(MultiplyReqDto.class);
		Mono<Response> multiply = mathService.multiply(reqDto);
		// 1st way
		return ServerResponse.ok()
				.body(multiply, Response.class);
		
		// 2nd way
		/*
		return serverRequest.bodyToMono(MultiplyReqDto.class)							
							.flatMap(monoReqDto -> {
								return mathService.multiply(Mono.just(monoReqDto));
							})
							.flatMap(res -> {
								return ServerResponse.ok().bodyValue(res);
							});
		*/
	}

}

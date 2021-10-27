package com.vinsguru.webfluxdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vinsguru.webfluxdemo.dto.FailedValidationResponse;
import com.vinsguru.webfluxdemo.exception.InputValidationException;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {
	
	@Autowired
	private RequestHandler requestHandler;
	
	@Bean
	public RouterFunction<ServerResponse> main() {
		return RouterFunctions.route()
				.path("router", this::serverResponseRouterFunction)
				.path("bouter", this::serverResponseRouterFunction)
				.build();
	}
	
	//@Bean
	public RouterFunction<ServerResponse> serverResponseRouterFunction(){
		return RouterFunctions.route()
							  .GET("/square/{input}", requestHandler::squareHandler)
							  .GET("/squarevalid/{input}", requestHandler::squareHandlerWithValidation)
							  .GET("/table/{input}", requestHandler::tableHandler)
							  .POST("/multiply", requestHandler::multiplyHandler)
							  .onError(InputValidationException.class, this::exceptionHandler)
							  .build();
	}
	
	//@Bean
	public RouterFunction<ServerResponse> serverResponseBouterFunction(){
		return RouterFunctions.route()
							  .GET("/square/{input}", requestHandler::squareHandler)
							  .GET("/squarevalid/{input}", requestHandler::squareHandlerWithValidation)
							  .GET("/table/{input}", requestHandler::tableHandler)
							  .POST("router/multiply", requestHandler::multiplyHandler)
							  .onError(InputValidationException.class, this::exceptionHandler)
							  .build();
	}
	
	private Mono<ServerResponse> exceptionHandler(Throwable t, ServerRequest s){
			InputValidationException exc = (InputValidationException) t;
			FailedValidationResponse res = FailedValidationResponse.builder().errorCode(400).input(exc.getInput()).message("Exception Man...!").build();
			return ServerResponse.badRequest().bodyValue(res);
	}

}

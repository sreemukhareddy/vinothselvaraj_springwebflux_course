package com.example.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.dto.TransactionRequestDto;
import com.example.orderservice.dto.TransactionResponseDto;
import com.example.orderservice.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class UserClient {
	
	private final WebClient webClient;
	
	

	public UserClient(@Value("${user.service.url}") String url) {
		this.webClient = WebClient.builder()
				             .baseUrl(url)
				             .build();
	}
	// user/transaction
	public Mono<TransactionResponseDto> authTransaction(TransactionRequestDto transactionRequestDto) {
		return this.webClient
		    .post()
		    .uri("/transaction")
		    .accept(MediaType.APPLICATION_NDJSON)
		    .bodyValue(transactionRequestDto)
		    .retrieve()
		    .bodyToMono(TransactionResponseDto.class);
	}
	
	public Flux<UserDto> getAllUsers() {
		return this.webClient
				   .get()
				   .uri("/all")
				   .accept(MediaType.APPLICATION_NDJSON)
				   .retrieve()
				   .bodyToFlux(UserDto.class);
				    
	}
	
	
}

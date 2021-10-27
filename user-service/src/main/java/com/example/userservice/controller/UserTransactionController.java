package com.example.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.dto.TransactionRequestDto;
import com.example.userservice.dto.TransactionResponseDto;
import com.example.userservice.entity.UserTransaction;
import com.example.userservice.service.UserTransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {
	
	@Autowired
	private UserTransactionService userTransactionService;
	
	@PostMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> monoTransactionReqDto){
		return monoTransactionReqDto.flatMap(this.userTransactionService::createTransaction);				                    
	}
	
	@GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<UserTransaction> getByUserId(@RequestParam Integer userId) {
		return userTransactionService.getByUserId(userId);
	}

}

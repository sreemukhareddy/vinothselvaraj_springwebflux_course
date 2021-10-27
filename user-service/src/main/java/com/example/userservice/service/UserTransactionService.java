package com.example.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.TransactionRequestDto;
import com.example.userservice.dto.TransactionResponseDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.UserTransactionRepository;
import com.example.userservice.util.EntityDtoUtil;
import com.example.userservice.dto.TransactionStatus;
import com.example.userservice.entity.UserTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserTransactionService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTransactionRepository userTransactionRepository;
	
	public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto requestDto) {
	return   this.userRepository
			.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
			.filter(b -> b.booleanValue() == true)
			.map(b -> EntityDtoUtil.toEntity(requestDto))
			.flatMap(userTransactionRepository::save)
			.map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
			.defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
	}
	
	public Flux<UserTransaction> getByUserId(int userId) {
		return this.userTransactionRepository.findByUserId(userId)
				;
				                  
	}

}

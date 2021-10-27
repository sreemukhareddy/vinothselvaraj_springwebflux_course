package com.example.userservice.util;

import org.springframework.beans.BeanUtils;

import com.example.userservice.dto.TransactionRequestDto;
import com.example.userservice.dto.TransactionResponseDto;
import com.example.userservice.dto.TransactionStatus;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.entity.UserTransaction;

public class EntityDtoUtil {
	
	public static UserDto toDto(UserEntity entity) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(entity, userDto);
		return userDto;
	}
	
	public static UserEntity dtoToEntity(UserDto userDto) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(userDto, entity);
		return entity;
	}
	
	public static UserTransaction toEntity(TransactionRequestDto transactionRequestDto) {
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setUserId(transactionRequestDto.getUserId());
		userTransaction.setAmount(transactionRequestDto.getAmount());
		return userTransaction;
	}
	
	public static TransactionResponseDto toDto(TransactionRequestDto transactionRequestDto, TransactionStatus status) {
		TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
		transactionResponseDto.setUserId(transactionRequestDto.getUserId());
		transactionResponseDto.setAmount(transactionRequestDto.getAmount());
		transactionResponseDto.setStatus(status);
		return transactionResponseDto;
	}
	
	

}

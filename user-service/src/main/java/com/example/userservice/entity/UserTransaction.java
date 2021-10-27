package com.example.userservice.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTransaction {

	@Id
	private Integer id;
	private Integer userId;
	private Integer amount;
	
}

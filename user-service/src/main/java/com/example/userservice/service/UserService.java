package com.example.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Flux<UserDto> all(){
		return this.userRepository.findAll()
								  .map(EntityDtoUtil::toDto);
	}
	
	public Mono<UserDto> getUserByID(Integer  id) {
		return this.userRepository.findById(id)
				   .map(EntityDtoUtil::toDto);
	}
	
	public Mono<UserDto> createUser(Mono<UserDto> userdtomono) {
		return userdtomono.map(EntityDtoUtil::dtoToEntity)
		           .flatMap(this.userRepository::save)
		           .map(EntityDtoUtil::toDto);
	}
	
	public Mono<UserDto> updateUser(Integer id, Mono<UserDto> userdtomono) {
		return this.userRepository.findById(id)
				                  .flatMap(userEntity -> {
				                	  return userdtomono.map(userdto -> {
				                		  userdto.setId(id);
				                		  return userdto;
				                	  });
				                  })
				                  .map(EntityDtoUtil::dtoToEntity)
				                  .flatMap(this.userRepository::save)
				                  .map(EntityDtoUtil::toDto);
	}
	
	public Mono<Void> deleteUserById(Integer id) {
		return this.userRepository.deleteById(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

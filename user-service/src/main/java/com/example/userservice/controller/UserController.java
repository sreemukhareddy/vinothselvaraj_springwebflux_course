package com.example.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<UserDto> all(){
		return this.userservice.all();	
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<UserDto>> getUser(@PathVariable Integer id) {
		return this.userservice.getUserByID(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping(produces =  MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<UserDto> createUser(@RequestBody Mono<UserDto> userdtomono) {
		return this.userservice.createUser(userdtomono);
	}
	
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable Integer id, @RequestBody Mono<UserDto> userdtomono) {
		return this.userservice.updateUser(id, userdtomono)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<Void> deleteUser(@PathVariable Integer id) {
		return this.userservice.deleteUserById(id);
	}
}

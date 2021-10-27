package com.example.userservice;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.UserTransactionRepository;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTransactionRepository userTransactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			
			for(int i = 0; i < 10; i++) {
				UserEntity userEntity = UserEntity.builder()
						  .name("111" + i)
						  .balance(1000)
						  .build();
				this.userRepository.save(userEntity).subscribe();
			}
			
			
		} catch (Exception e) {
			
		}
	}
	
	@PreDestroy
	public void destroy() {
		this.userTransactionRepository.deleteAll().subscribe();
		this.userRepository.deleteAll().subscribe();
	}

}

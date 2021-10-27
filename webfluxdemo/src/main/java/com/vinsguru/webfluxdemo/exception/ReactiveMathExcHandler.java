package com.vinsguru.webfluxdemo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vinsguru.webfluxdemo.dto.FailedValidationResponse;

@ControllerAdvice
public class ReactiveMathExcHandler {
	
	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<FailedValidationResponse> exceptionHandler(InputValidationException exc) {
		FailedValidationResponse failedValidationResponse = FailedValidationResponse.builder()
		.errorCode(400)
		.input(exc.getInput())
		.message(exc.getMessage())
		.build();
		return ResponseEntity.badRequest().body(failedValidationResponse);
	}

}

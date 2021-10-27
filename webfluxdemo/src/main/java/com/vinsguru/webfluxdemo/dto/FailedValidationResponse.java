package com.vinsguru.webfluxdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FailedValidationResponse {

	private int errorCode;
	private int input;
	private String message;
}

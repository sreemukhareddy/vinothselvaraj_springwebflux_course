package com.vinsguru.webfluxdemo.exception;

import lombok.Data;

@Data
public class InputValidationException extends RuntimeException{
	private static final String MSG = "alloed range is 10-20";
	private static final int errorCode = 100;
	private final int input;
	public InputValidationException(int input) {
		super(MSG);
		this.input = input;
	}
}

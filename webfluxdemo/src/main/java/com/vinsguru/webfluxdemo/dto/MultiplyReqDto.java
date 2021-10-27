package com.vinsguru.webfluxdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class MultiplyReqDto {
	
	private int first;
	private int second;

}

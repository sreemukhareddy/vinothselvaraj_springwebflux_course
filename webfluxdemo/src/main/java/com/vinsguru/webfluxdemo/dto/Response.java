package com.vinsguru.webfluxdemo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response {
	
	private Date date;
	private int output;
	
	public Response(int output) {
		this.output = output;
	}

}

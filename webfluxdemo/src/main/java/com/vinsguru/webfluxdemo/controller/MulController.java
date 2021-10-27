package com.vinsguru.webfluxdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.service.MathService;

@RestController
@RequestMapping("/math")
public class MulController {
	
	@Autowired
	private MathService mathService;
	
	@GetMapping("/square/{input}")
	public Response square(@PathVariable Integer input) {
		return mathService.findsquare(input);
	}
	
	@GetMapping("/table/{input}")
	public List<Response> multiplication(@PathVariable Integer input) {
		return mathService.multiplication(input);
	}

}

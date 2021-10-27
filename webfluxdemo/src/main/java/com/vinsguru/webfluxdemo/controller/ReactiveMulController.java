package com.vinsguru.webfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinsguru.webfluxdemo.dto.MultiplyReqDto;
import com.vinsguru.webfluxdemo.dto.Response;
import com.vinsguru.webfluxdemo.exception.InputValidationException;
import com.vinsguru.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactivemath")
public class ReactiveMulController {
	
	@Autowired
	private ReactiveMathService mathService;
	
	@GetMapping("/square/{input}")
	public Mono<Response> square(@PathVariable Integer input) {
		return mathService.findsquare(input);
	}
	
	@GetMapping(value = "/table/{input}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> multiplication(@PathVariable Integer input) {
		if(input > 20 || input < 10) {
			throw new InputValidationException(input);
		}
		return mathService.table(input);
	}
	
	@PostMapping(value = "/table")
	public Mono<Response> multiplication(@RequestBody Mono<MultiplyReqDto> reqDto) {
		return mathService.multiply(reqDto);
	}
	
	@GetMapping(value = "/search")
	public Mono<Response> searchJobs(@RequestParam Integer count, @RequestParam Integer page) {
		return Mono.just(count * page)
				.map(i -> {
					Response res = new Response();
					res.setOutput(i);
					return res;
				});
				
	}


}

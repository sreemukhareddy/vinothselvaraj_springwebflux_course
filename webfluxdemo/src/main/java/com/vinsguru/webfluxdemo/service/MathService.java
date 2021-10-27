package com.vinsguru.webfluxdemo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.vinsguru.webfluxdemo.Util;
import com.vinsguru.webfluxdemo.dto.Response;

@Service
public class MathService {

	public Response findsquare(int num) {
		return new Response(num * num);
	}

	public List<Response> multiplication(int num) {
		return IntStream.rangeClosed(1, 10)
				.peek(i -> Util.sleep(1))
				.mapToObj(i -> new Response(i * num))
				.collect(Collectors.toList());
	}

}

package com.vinsguru.webfluxdemo.prorea;

import reactor.core.publisher.Mono;

public class ContextTest {

	public static void main(String[] args) {
		Mono.deferContextual(ctx -> {
			if (ctx.hasKey("user"))
				return Mono.just("hello");
			return Mono.error(new RuntimeException("asdadads"));
		}).contextWrite(ctx -> ctx.put("users", "user")).subscribe(System.out::println);
	}

}

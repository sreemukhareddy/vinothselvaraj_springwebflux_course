package com.vinsguru.webfluxdemo.prorea;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class Test1 {
	
	public static void main(String[] args) {
		
		// handle through which we emit items
		Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
		
		// handle through which subscribers will recevie items
		Flux<String> flux1 = sink.asFlux();
		
		flux1
		.doOnComplete(() -> {
			System.out.println("Completed");
		})
		.subscribe(System.out::println)
		;
		
		flux1
		.doOnComplete(() -> {
			System.out.println("Completed");
		})
		.subscribe(System.out::println)
		;

		
		sink.tryEmitNext("asfasf1");
		sink.tryEmitNext("asfasf2");
		flux1
		.doOnComplete(() -> {
			System.out.println("Completed");
		})
		.subscribe(System.out::println)
		;
		sink.tryEmitNext("asfasf3");
		sink.tryEmitComplete();
	}

}

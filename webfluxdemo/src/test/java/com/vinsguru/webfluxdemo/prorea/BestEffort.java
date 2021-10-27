package com.vinsguru.webfluxdemo.prorea;

import java.time.Duration;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class BestEffort {
	
	public static void main(String[] args) throws InterruptedException {
		//System.setProperty("", "");
		Sinks.Many<String> sink = Sinks.many().multicast().directBestEffort();
		
		Flux<String> flux = sink.asFlux();
		
		flux.subscribe(s -> System.out.println("Sam " + s));
		flux.delayElements(Duration.ofSeconds(1)).subscribe(s -> System.out.println("Mike " + s));
		
		IntStream.rangeClosed(1, 100)
		.forEach(i -> sink.tryEmitNext(i + ""));
		
		Thread.sleep(100000);
	}

}

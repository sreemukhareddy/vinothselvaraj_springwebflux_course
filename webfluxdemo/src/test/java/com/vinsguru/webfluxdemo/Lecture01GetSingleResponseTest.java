package com.vinsguru.webfluxdemo;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.vinsguru.webfluxdemo.dto.MultiplyReqDto;
import com.vinsguru.webfluxdemo.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lecture01GetSingleResponseTest extends BaseTest{
	
	@Autowired
	private WebClient webClient;
	
	@Test
	public void blockTest() {
		Response response = webClient.get()
				 .uri("/reactivemath/square/{input}", 5)
				 .accept(MediaType.APPLICATION_NDJSON)
				 .retrieve()
				 .bodyToMono(Response.class)
				 .block();
		
		System.out.println(response);
				 
	}
	
	@Test
	public void stepVerifierTest() {
		Mono<Response> mono = webClient.get()
				 .uri("/reactivemath/square/{input}", 5)
				 .accept(MediaType.APPLICATION_NDJSON)
				 .retrieve()
				 .bodyToMono(Response.class);
		

		StepVerifier.create(mono)
					.expectNextCount(1)
					.expectComplete();
	}
	
	/* wrong */
	@Test
	public void getMultiResTest() {
			List<Response> responses = webClient.get()
				 .uri("/reactivemath/table/{input}", 15)
				 .accept(MediaType.APPLICATION_NDJSON)
				 .retrieve()
				 .bodyToFlux(Response.class)
				 .collectList()
				 .block();
		
		System.out.println(responses);		 
	}
	
	// correct
	@Test
	public void getMultiResTest2() {
			Mono<List<Response>> responses = webClient.get()
				 .uri("/reactivemath/table/{input}", 15)
				 .accept(MediaType.APPLICATION_NDJSON)
				 .retrieve()
				 .bodyToFlux(Response.class)
				 .collectList();
		
		System.out.println(responses.subscribe(System.out::println));		 
	}
	
	@Test
	public void getMultiResTest3() {
			Flux<Response> responseFlux = webClient.get()
				 .uri("/reactivemath/table/{input}", 15)
				 //.header("content-type", MediaType.APPLICATION_NDJSON.getType())
				 .retrieve()
				 .bodyToFlux(Response.class)
				 .doOnNext(System.out::println);
			
			StepVerifier.create(responseFlux)
						.expectNextCount(10)
						.verifyComplete();
	}
	
	@Test
	public void postTest() {
		MultiplyReqDto req = new MultiplyReqDto();
		req.setFirst(2);req.setSecond(13);
			Mono<Response> responseFlux = webClient.post()
				 .uri("/reactivemath/table/")
				 .bodyValue(req)
				 //.header("content-type", MediaType.APPLICATION_NDJSON.getType())
				 .retrieve()
				 .bodyToMono(Response.class)
				 .doOnNext(System.out::println);
			
			StepVerifier.create(responseFlux)
						.expectNextCount(1)
						.verifyComplete();
	}
	
	@Test
	public void postTestValidationException() {
		MultiplyReqDto req = new MultiplyReqDto();
		req.setFirst(2);req.setSecond(3);
			Mono<Response> responseFlux = webClient.post()
				 .uri("/reactivemath/table/")
				 .bodyValue(req)
				 //.header("content-type", MediaType.APPLICATION_NDJSON.getType())
				 .retrieve()
				 .bodyToMono(Response.class)
				 .doOnNext(System.out::println);
			responseFlux = webClient.post()
			 .uri("/reactivemath/table/").bodyValue(req).exchangeToMono(cr -> {
				if (cr.statusCode().is4xxClientError()) {
					return Mono.error(new RuntimeException());
				} else {
				 return cr.bodyToMono(Response.class);
				}
			 });
			 
			StepVerifier.create(responseFlux)
						.verifyError();
	}
	
	@Test
	public void queryParam() {
		String query = "http://localhost:8080/reactivemath/search?count={count}&page={page}";
		URI uri = UriComponentsBuilder.fromUriString(query)
		.build(10, 20);
		Mono<Response> response = webClient.get()
				 .uri(uri)
				 .retrieve()
				 .bodyToMono(Response.class);
		
		StepVerifier.create(response).expectComplete();
				 
	}

}

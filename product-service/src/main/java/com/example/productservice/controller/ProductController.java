package com.example.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<ProductDTO> all(){
		return this.productService.getAll();
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable String id){
		return this.productService.getProductById(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ProductDTO> insertProduct(@RequestBody Mono<ProductDTO> productMono) {
		return this.productService.insertProduct(productMono);
	}
	
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDTO> productMono) {
		return this.productService.updateProduct(id, productMono)
				   .map(ResponseEntity::ok)
				   .defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Mono<Void> deleteProduct(@PathVariable String id) {
		return this.productService.deleteProduct(id);
	}
	
	@GetMapping(path = "/price-range", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<ProductDTO> getProductWithInRange(@RequestParam String min, @RequestParam String max) {
		return this.productService.productsInRange(min, max);
	}

}

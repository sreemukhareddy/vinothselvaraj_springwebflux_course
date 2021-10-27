package com.example.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Flux<ProductDTO> getAll(){
		return this.productRepository
				   .findAll()
				   .map(EntityDtoUtil::toDto);
	}
	
	public Mono<ProductDTO> getProductById(String id) {
		return this.productRepository.findById(id)
				   .map(EntityDtoUtil::toDto);
	}
	
	public Mono<ProductDTO> insertProduct(Mono<ProductDTO> productDTOMono) {
		/*
		 * return monoProductDTO.flatMap(productDTO -> { return
		 * this.productRepository.save(EntityDtoUtil.toEntity(productDTO))
		 * .map(EntityDtoUtil::toDto); });
		 */
		
		return productDTOMono.map(EntityDtoUtil::toEntity)
				.flatMap(this.productRepository::insert)
				.map(EntityDtoUtil::toDto);
	}
	
	public Mono<ProductDTO> updateProduct(String id, Mono<ProductDTO> productDTOMono) {
		return this.productRepository.findById(id)
				   .flatMap(product -> {
					   return productDTOMono.map(EntityDtoUtil::toEntity)
					   .map(p -> {
						   p.setId(id);
						   return p;
					   });
				   })
				   .flatMap(this.productRepository::save)
				   .map(EntityDtoUtil::toDto)
				   ;
	}
	
	public Mono<Void> deleteProduct(String id) {
		return this.productRepository.deleteById(id);
	}

	public Flux<ProductDTO> productsInRange(String min, String max) {
		return this.productRepository.findAll()
			.filter(p -> p.getPrice() > Integer.valueOf(min) && p.getPrice() < Integer.valueOf(max))
			.map(EntityDtoUtil::toDto);
	}
	
	
	
	
	
	
	
	
}

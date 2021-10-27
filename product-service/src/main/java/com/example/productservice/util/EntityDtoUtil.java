package com.example.productservice.util;

import org.springframework.beans.BeanUtils;

import com.example.productservice.dto.ProductDTO;
import com.example.productservice.entity.Product;

public class EntityDtoUtil {
	
	public static ProductDTO toDto(Product entity) {
		ProductDTO productDTO = new ProductDTO();
		BeanUtils.copyProperties(entity, productDTO);
		return productDTO;
	}
	
	public static Product toEntity(ProductDTO productDTO) {
		Product productEntity = new Product();
		BeanUtils.copyProperties(productDTO, productEntity);
		return productEntity;
	}

}

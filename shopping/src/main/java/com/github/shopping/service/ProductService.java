package com.github.shopping.service;

import com.github.shopping.dto.ProductDetailDto;
import com.github.shopping.dto.ProductDto;
import com.github.shopping.entity.Product;
import org.springframework.data.domain.Page; // 올바른 import
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ProductService {
    Page<ProductDto> getAllProducts(Pageable pageable); // 전체 제품 조회

    Optional<ProductDetailDto> getProductDetailById(Long id);

    int adjustQuantity(Long productId, int changeAmount);

    Product checkAndUpdateProductStock(Long productId, int quantity);

    Product findById(Long productId);
}



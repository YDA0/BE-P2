package com.github.shopping.service;

import com.github.shopping.dto.ProductDto;
import com.github.shopping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    // 상품 전체 조회 메서드
    Page<ProductDto> getAllProducts(Pageable pageable);
}



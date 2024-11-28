package com.github.shopping.service;

import com.github.shopping.dto.ProductDto;
import org.springframework.data.domain.Page; // 올바른 import


public interface ProductService {

    Page<ProductDto> getAllProducts(int page, int size);
}

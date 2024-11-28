package com.github.shopping.service;

import com.github.shopping.dto.ProductDto;
import com.github.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;


    @Override
    public Page<ProductDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(ProductDto::new);
    }
}


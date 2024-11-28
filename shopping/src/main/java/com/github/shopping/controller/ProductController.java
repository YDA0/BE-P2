package com.github.shopping.controller;

import com.github.shopping.dto.ProductDto;
import com.github.shopping.repository.ProductRepository;
import com.github.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    // 1. 쇼핑몰 전체 물품 조회
    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

}

package com.github.shopping.controller;

import com.github.shopping.entity.Product;
import com.github.shopping.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.shopping.dto.ProductDto;
import com.github.shopping.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final PagedResourcesAssembler<ProductDto> pagedResourcesAssembler;

    public ProductController(ProductService productService, ProductRepository productRepository, PagedResourcesAssembler<ProductDto> pagedResourcesAssembler) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/products")
    public PagedModel<EntityModel<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,  // 페이지가 0부터 시작하도록 기본값 설정
            @RequestParam(defaultValue = "10") int size) {  // 한 페이지에 10개씩
        Pageable pageable = PageRequest.of(page, size);  // 0-based index로 처리
        Page<ProductDto> productDtoPage = productService.getAllProducts(pageable);

        return pagedResourcesAssembler.toModel(productDtoPage); // HATEOAS 형식으로 반환
    }

}
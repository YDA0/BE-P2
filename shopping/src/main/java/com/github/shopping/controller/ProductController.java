package com.github.shopping.controller;

import com.github.shopping.dto.ProductDetailDto;
import com.github.shopping.entity.Product;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.shopping.dto.ProductDto;
import com.github.shopping.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel<ProductDetailDto>> getProductDetail(@PathVariable Long id) {
        ProductDetailDto productDetailDto = productService.getProductDetailById(id)
                .orElseThrow(() -> new NotFoundException("이 제품의 상품 정보를 찾을 수 없습니다."));

        // HATEOAS 링크 추가
        EntityModel<ProductDetailDto> resource = EntityModel.of(
                productDetailDto,
                Link.of("/api/products/" + id).withSelfRel(),  // 현재 리소스 링크
                Link.of("/api/products").withRel("all-products")  // 전체 상품 목록 보기 링크
        );

        return ResponseEntity.ok(resource);  // 200 OK 상태 코드로 반환
    }

}
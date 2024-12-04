package com.github.shopping.controller;

import com.github.shopping.dto.AddToCartRequestDto;
import com.github.shopping.dto.ProductDetailDto;
import com.github.shopping.entity.Cart;
import com.github.shopping.exceptions.ResponseMessage;
import com.github.shopping.mapper.CartMapper;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.repository.ProductRepository;
import com.github.shopping.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.shopping.dto.ProductDto;
import com.github.shopping.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final PagedResourcesAssembler<ProductDto> pagedResourcesAssembler;
    private final CartService cartService;
    private final CartMapper cartMapper;


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


    // 상품 수량 증감 API (상세 페이지에서 수량 조정)
    @PatchMapping("products/{id}/quantity")
    public ResponseEntity<?> adjustQuantity(@PathVariable Long id, @RequestParam int changeAmount) {
        try {
            // 수량 증감 로직을 처리
            int newQuantity = productService.adjustQuantity(id, changeAmount);

            return ResponseEntity.ok(new ResponseMessage("수량 조정 성공", newQuantity));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseMessage("수량 조정 실패", e.getMessage()));
        }
    }

    // 장바구니에 물품 추가 API
    @PostMapping("products/{id}/cart")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestBody AddToCartRequestDto dto) {
        try {
            // 장바구니에 상품 추가
            Cart cart = cartService.addToCart(dto);

            // 성공적으로 장바구니에 추가되면 201 Created 응답
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseMessage("물건 담기 성공", cart));

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("물건 담기 실패", e.getMessage()));
        }
    }





    }




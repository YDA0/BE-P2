package com.github.shopping.service;

import com.github.shopping.dto.OptionDto;
import com.github.shopping.dto.ProductDetailDto;
import com.github.shopping.dto.ProductDto;
import com.github.shopping.entity.Product;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.mapper.ProductMapper;
import com.github.shopping.repository.OptionRepository;
import com.github.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final ProductMapper productMapper;


    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        // productStock이 0보다 큰 데이터만 조회
        Page<Product> productPage = productRepository.findByProductStockGreaterThan(0, pageable);

        // Product 엔티티 -> ProductDto 변환
        return productPage.map(this::convertToDto);
    }

    public Optional<ProductDetailDto> getProductDetailById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("이 제품의 상품 정보를 찾을 수 없습니다."));

        ProductDetailDto productDetailDto = productMapper.toProductDetailDto(product);

        // colorOptions, sizeOptions 세팅
        List<OptionDto> colorOptions = productMapper.toColorOptions(product.getOptions());
        List<OptionDto> sizeOptions = productMapper.toSizeOptions(product.getOptions());

        productDetailDto.setColorOptions(colorOptions);
        productDetailDto.setSizeOptions(sizeOptions);

        return Optional.of(productDetailDto);
    }


    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getTitle(),
                product.getPrice(),
                product.getContents(),
                product.getProductUpdateAt(),
                product.getProductImageUrl()
        );
    }

    // 상품의 수량을 증감하는 메서드
    public int adjustQuantity(Long productId, int changeAmount) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."));

        int newQuantity = product.getProductStock() + changeAmount; // 재고 수량 조정

        if (newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");
        }

        product.setProductStock(newQuantity); // 새로운 재고 수량 설정
        productRepository.save(product); // 업데이트된 상품 저장

        return newQuantity; // 새로운 재고 수량 반환
    }

    @Transactional
    public Product checkAndUpdateProductStock(Long productId, int quantity) {
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."));

        // 재고 수량 확인
        if (product.getProductStock() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");
        }

        // 재고 수량 감소
        product.setProductStock(product.getProductStock() - quantity);

        // 변경된 상품 저장
        return productRepository.save(product);
    }

}
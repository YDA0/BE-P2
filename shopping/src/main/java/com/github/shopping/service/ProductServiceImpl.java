package com.github.shopping.service;

import com.github.shopping.dto.OptionDto;
import com.github.shopping.dto.ProductDetailDto;
import com.github.shopping.dto.ProductDto;
import com.github.shopping.entity.Product;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;


    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        // productStock이 0보다 큰 데이터만 조회
        Page<Product> productPage = productRepository.findByProductStockGreaterThan(0, pageable);

        // Product 엔티티 -> ProductDto 변환
        return productPage.map(this::convertToDto);
    }

    public Optional<ProductDetailDto> getProductDetailById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("이 제품의 상품 정보를 찾을 수 없습니다." + id));

        ProductDetailDto dto = new ProductDetailDto();
        dto.setProductId(product.getProductId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setContents(product.getContents());
        dto.setProductStock(product.getProductStock());
        dto.setProductImageUrl(product.getProductImageUrl());

        // 색상 옵션 (예시)
        List<OptionDto> colorOptions = new ArrayList<>();
        colorOptions.add(new OptionDto("색상", "Red"));
        colorOptions.add(new OptionDto("색상", "Blue"));
        dto.setColorOptions(colorOptions);

        // 사이즈 옵션 (예시)
        List<OptionDto> sizeOptions = new ArrayList<>();
        sizeOptions.add(new OptionDto("사이즈", "S"));
        sizeOptions.add(new OptionDto("사이즈", "M"));
        sizeOptions.add(new OptionDto("사이즈", "L"));
        dto.setSizeOptions(sizeOptions);

        return Optional.of(dto);
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

}


package com.github.shopping.service;

import com.github.shopping.dto.ProductDto;
import com.github.shopping.entity.Product;
import com.github.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getTitle(),
                product.getPrice(),
                product.getContents(),
                product.getProductStatus(),
                product.getProductStock(),
                product.getProductCreateAt(),
                product.getProductUpdateAt(),
                product.getProductImageUrl()
        );
    }
}


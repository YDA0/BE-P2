package com.github.shopping.service;

import com.github.shopping.dto.ProductDto;
import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.Product;
import com.github.shopping.entity.User;
import com.github.shopping.repository.ProductRepository;
import com.github.shopping.repository.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        // productStock이 0보다 큰 데이터만 조회
        Page<Product> productPage = productRepository.findByProductStockGreaterThan(0, pageable);

        return productPage.map(this::convertToDto);
    }

    private ProductDto convertToDto(Product product) {
        List<SellDto> sellDtoList = product.getSells().stream()
                .map(sell -> new SellDto(
                        sell.getSellId(),
                        sell.getUserId().getUserId(),
                        sell.getSellName(),
                        sell.getSellStock(),
                        sell.getSellPrice(),
                        sell.getSellImage(),
                        sell.getSellCreateAt(),
                        sell.getSellUpdateAt()
                ))
                .collect(Collectors.toList());

        return new ProductDto(
                product.getProductId(),
                product.getTitle(),
                product.getPrice(),
                product.getContents(),
                product.getProductStatus(),
                product.getProductStock(),
                product.getProductCreateAt(),
                product.getProductUpdateAt(),
                product.getProductImageUrl(),
                sellDtoList
        );
    }
}


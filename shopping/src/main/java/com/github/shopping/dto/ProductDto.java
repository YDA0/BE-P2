package com.github.shopping.dto;

import com.github.shopping.entity.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.shopping.entity.ProductImage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {
    private Long productId;
    private String title;
    private Integer price;
    private String contents;
    private String productStatus;
    private Integer productStock;
    private LocalDateTime productCreateAt;
    private LocalDateTime productUpdateAt;
    private List<String> images;


    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.contents = product.getContents();
        this.productStatus = product.getProductStatus();
        this.productCreateAt = product.getProductCreateAt();
        this.productUpdateAt = product.getProductUpdateAt();
        this.productStock = product.getProductStock();

        // Product에서 ProductImage를 가져와서 이미지 URL 리스트 생성
        this.images = product.getProductImages().stream()
                .map(ProductImage::getImageUrl)  // ProductImage에서 imageUrl만 가져옴
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return price == that.price && productStock == that.productStock && Objects.equals(productId, that.productId) && Objects.equals(title, that.title) && Objects.equals(contents, that.contents) && Objects.equals(productStatus, that.productStatus) && Objects.equals(productCreateAt, that.productCreateAt) && Objects.equals(productUpdateAt, that.productUpdateAt) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, contents, productStatus, productStock, productCreateAt, productUpdateAt, images);
    }
}

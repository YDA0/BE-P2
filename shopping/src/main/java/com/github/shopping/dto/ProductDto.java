package com.github.shopping.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class ProductDto {
    private Long productId;
    private String title;
    private Integer price;
    private String contents;
    private String productStatus;
    private Integer productStock;
    private LocalDateTime productCreateAt;
    private LocalDateTime productUpdateAt;
    private String productImageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(productId, that.productId) && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(contents, that.contents) && Objects.equals(productStatus, that.productStatus) && Objects.equals(productStock, that.productStock) && Objects.equals(productCreateAt, that.productCreateAt) && Objects.equals(productUpdateAt, that.productUpdateAt) && Objects.equals(productImageUrl, that.productImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, contents, productStatus, productStock, productCreateAt, productUpdateAt, productImageUrl);
    }
}

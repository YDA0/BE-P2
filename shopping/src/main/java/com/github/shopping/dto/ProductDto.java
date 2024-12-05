package com.github.shopping.dto;

import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@Table(name = "Product")
public class ProductDto {
    private Long productId;
    private String title;
    private Integer price;
    private String contents;
    private LocalDateTime productUpdateAt;
    private String productImageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(productId, that.productId) && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(contents, that.contents) && Objects.equals(productUpdateAt, that.productUpdateAt) && Objects.equals(productImageUrl, that.productImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, title, price, contents, productUpdateAt, productImageUrl);
    }
}

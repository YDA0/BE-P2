package com.github.shopping.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDto {
    private Long productId;   // 상품 ID

    private String title;     // 상품 제목

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    private Integer price;     // 상품 가격

    private String selectedColor; // 선택한 색상

    private String selectedSize;  // 선택한 사이즈

    private Integer quantity;     // 수량
}

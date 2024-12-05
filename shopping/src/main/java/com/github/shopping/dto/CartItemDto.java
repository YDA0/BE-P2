package com.github.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private String title;        // 상품 제목
    private String colorOption;  // 선택한 색상
    private String sizeOption;   // 선택한 사이즈
    private Integer quantity;    // 수량
    private Integer cartPrice;   // 해당 상품 가격 (가격 * 수량)
}
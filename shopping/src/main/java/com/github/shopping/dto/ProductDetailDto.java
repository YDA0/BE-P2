package com.github.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long productId;
    private String title;
    private Integer price;
    private String contents;
    private Integer productStock;
    private String productImageUrl;
    private List<OptionDto> colorOptions;
    private List<OptionDto> sizeOptions;
}
package com.github.shopping.mapper;

import com.github.shopping.dto.AddToCartRequestDto;
import com.github.shopping.entity.Cart;
import com.github.shopping.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    // AddToCartRequestDto -> Cart 변환
    @Mapping(target = "product", source = "product")  // product는 서비스에서 찾아서 넘겨줍니다.
    @Mapping(target = "quantity", source = "dto.quantity")
    @Mapping(target = "colorOption", source = "dto.selectedColor")
    @Mapping(target = "sizeOption", source = "dto.selectedSize")
    @Mapping(target = "cartPrice", expression = "java(dto.getPrice() * dto.getQuantity())")  // 가격 계산
    Cart toCart(AddToCartRequestDto dto, Product product);
}
package com.github.shopping.service;

import com.github.shopping.dto.AddToCartRequestDto;
import com.github.shopping.dto.CartItemDto;
import com.github.shopping.entity.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(AddToCartRequestDto dto);

    Cart updateQuantity(Long cartId, int changeAmount);

    List<CartItemDto> getCartItems();
}

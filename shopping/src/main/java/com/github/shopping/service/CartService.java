package com.github.shopping.service;

import com.github.shopping.dto.AddToCartRequestDto;
import com.github.shopping.entity.Cart;

public interface CartService {
    Cart addToCart(AddToCartRequestDto dto);

    Cart updateQuantity(Long cartId, int changeAmount);
}

package com.github.shopping.repository;

import com.github.shopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByProduct_ProductIdAndColorOptionAndSizeOption(Long productId, String colorOption, String sizeOption);
}

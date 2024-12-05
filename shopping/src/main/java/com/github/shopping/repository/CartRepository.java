package com.github.shopping.repository;

import com.github.shopping.entity.Cart;
import com.github.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByProductAndColorOptionAndSizeOption(
            Product product, String colorOption, String sizeOption
    );

}

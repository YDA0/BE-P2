package com.github.shopping.repository;

import com.github.shopping.entity.Product;
import org.springframework.data.domain.Page; // 올바른 import
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductStockGreaterThan(int productStock, Pageable pageable);

}

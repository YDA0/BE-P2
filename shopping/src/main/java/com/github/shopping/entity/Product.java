package com.github.shopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;  // 권한 ID

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 상품명

    @Column(name = "price", nullable = false)
    private Integer price; // 제품 가격

    @Column(name = "contents", columnDefinition = "TEXT")  // 상품 설명 컬럼 (TEXT 타입)
    private String contents;


    @Column(name = "product_update_at") // 상품 수정일 (자동 갱신)
    @UpdateTimestamp // 상품 수정 시 자동으로 시간 갱신
    private LocalDateTime productUpdateAt;


    @Column(name = "product_stock")
    private Integer productStock;


    @Column(name = "product_image_url")
    private String productImageUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<Option> options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @OneToMany(mappedBy = "product")
    private List<Sell> sells;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cart;
}


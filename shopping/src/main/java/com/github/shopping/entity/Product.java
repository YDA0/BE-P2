package com.github.shopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @Column(name = "product_status", nullable = false, length = 1)  // 상품 상태 (CHAR(1))
    private String productStatus;

    @Column(name = "product_create_at", nullable = false, updatable = false) // 상품 등록일 (자동 설정)
    @CreationTimestamp // 상품 등록 시 자동으로 시간 설정
    private LocalDateTime productCreateAt;

    @Column(name = "product_update_at") // 상품 수정일 (자동 갱신)
    @UpdateTimestamp // 상품 수정 시 자동으로 시간 갱신
    private LocalDateTime productUpdateAt;

    @Column(name = "product_delete_at") // 상품 삭제일 (논리 삭제)
    private LocalDateTime deleteAt;

    @Column(name = "product_stock")
    private Integer productStock;

    @Column(nullable = false)
    private Boolean isDelete = false;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;


}


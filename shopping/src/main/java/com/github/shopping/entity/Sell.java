package com.github.shopping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sell")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sell_id")
    private Long sellId; // 판매 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;  // 판매자는 사용자

    @Column(name = "sell_name", nullable = false)
    private String sellName;

    @Column(name = "sell_stock", nullable = false)
    private Integer sellStock;

    @Column(name = "sell_price", nullable = false)
    private Double sellPrice;

    @Column(name = "sell_image", nullable = false)
    private String sellImage;

    @Column(name = "sell_create_at")
    private LocalDateTime sellCreateAt;

    @Column(name = "sell_update_at")
    private LocalDateTime sellUpdateAt;

    @PrePersist
    protected void onCreate() {
        this.sellCreateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.sellUpdateAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // 판매는 상품에 대한 정보
}
package com.github.shopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Setter
@Slf4j
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;  // 해당 옵션이 속한 상품

    private String optionType;  // 옵션 종류 (색상, 사이즈 등)
    private String optionValue; // 옵션 값 (예: 빨강, L)

}

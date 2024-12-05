package com.github.shopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentItem_id")
    private Long paymentItemId; // 아이템 ID

    private String paymentItemTitle; // 상품 제목

    private Integer quantity; // 구매 수량

    private Integer paymentItemPrice; // 상품 가격 (단가)

    private String colorOption; // 색상 옵션

    private String sizeOption; // 사이즈 옵션
}


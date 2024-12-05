package com.github.shopping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId; // 결제 ID

    @Column(name = "payment_email")
    private String paymentEmail; // 결제한 사용자 이메일

    @NotNull
    private String cardNumber; // 카드 번호

    @NotNull
    private String expiryDate; // 유효기간

    @NotNull
    private String cvc; // CVC 코드

    private String address; // 주소

    private String zipCode; // 우편번호

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private List<PaymentItem> paymentItems = new ArrayList<>(); // 구매한 물품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

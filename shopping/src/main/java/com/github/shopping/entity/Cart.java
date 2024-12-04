package com.github.shopping.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 장바구니 항목 고유 ID

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST) // Cascade 옵션 추가
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity; // 수량

    private String colorOption; // 색상 옵션

    private String sizeOption; // 사이즈 옵션

    @NotNull
    @Column(name = "cart_price")
    private Integer cartPrice; // 가격 (상품 가격 * 수량)

    // 기본 생성자, getter, setter는 Lombok을 사용하여 자동 생성됩니다.

    // 장바구니 항목 가격을 계산하는 메서드
    public void calculatePrice() {
        if (product != null && product.getPrice() != null) {
            this.cartPrice = product.getPrice() * quantity;
        } else {
            // price가 null일 경우 기본값 설정
            this.cartPrice = 0;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(product, cart.product) && Objects.equals(quantity, cart.quantity) && Objects.equals(colorOption, cart.colorOption) && Objects.equals(sizeOption, cart.sizeOption) && Objects.equals(cartPrice, cart.cartPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, colorOption, sizeOption, cartPrice);
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}

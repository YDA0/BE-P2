package com.github.shopping.controller;

import com.github.shopping.dto.CartItemDto;
import com.github.shopping.dto.PaymentRequestDto;
import com.github.shopping.exceptions.ResponseMessage;
import com.github.shopping.service.CartService;
import com.github.shopping.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final CartService cartService;

    private final PaymentService paymentService;

    @GetMapping("/payment")
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        List<CartItemDto> cartItems = cartService.getCartItems(); // CartService에서 장바구니 아이템 가져오기
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> processPayment(@RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        // 결제 요청 처리 (서비스 호출)
        paymentService.processPayment(paymentRequestDto);

        // 성공 응답 반환
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("결제가 완료되었습니다."));
    }
}
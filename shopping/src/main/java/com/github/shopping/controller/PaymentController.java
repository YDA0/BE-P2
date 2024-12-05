package com.github.shopping.controller;

import com.github.shopping.dto.CartItemDto;
import com.github.shopping.dto.PaymentRequestDto;
import com.github.shopping.exceptions.PaymentProcessingException;
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
        try {
            // 결제 요청 처리 (서비스 호출)
            paymentService.processPayment(paymentRequestDto);

            // 성공 응답 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("결제가 완료되었습니다."));
        } catch (PaymentProcessingException e) {
            // 결제 처리 예외 발생 시 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("결제 처리 중 오류가 발생했습니다. 다시 시도해주세요."));
        } catch (Exception e) {
            // 일반적인 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage("잘못된 요청입니다. 오류: " + e.getMessage()));
        }
    }
}
package com.github.shopping.service;

import com.github.shopping.dto.CartItemDto;
import com.github.shopping.dto.PaymentRequestDto;
import com.github.shopping.entity.Payment;
import com.github.shopping.entity.PaymentItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.shopping.repository.PaymentRepository;
import com.github.shopping.mapper.PaymentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final CartService cartService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;// CartItemMapper 주입

    public Payment processPayment(PaymentRequestDto paymentRequest) {
        // 1. 장바구니 아이템 조회
        List<CartItemDto> cartItems = cartService.getCartItems();

        // 2. Payment 엔티티 생성 및 정보 설정
        Payment payment = paymentMapper.toPayment(paymentRequest);

        // 3. CartItemDto 리스트를 PaymentItem 리스트로 변환
        List<PaymentItem> paymentItems = cartItems.stream()
                .map(paymentMapper::toPaymentItem)  // CartItemDto -> PaymentItem 변환
                .collect(Collectors.toList());

        payment.setPaymentItems(paymentItems);

        // 4. 결제 데이터 저장
        return paymentRepository.save(payment);
    }
}
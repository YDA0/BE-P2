package com.github.shopping.service;

import com.github.shopping.dto.CartItemDto;
import com.github.shopping.dto.PaymentRequestDto;
import com.github.shopping.entity.Payment;
import com.github.shopping.entity.PaymentItem;
import com.github.shopping.entity.User;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;

    public Payment processPayment(PaymentRequestDto paymentRequest) {
        // 1. 장바구니 아이템 조회
        List<CartItemDto> cartItems = cartService.getCartItems();

        // 2. 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다: " + email));

        // 3. Payment 엔티티 생성 및 정보 설정
        Payment payment = paymentMapper.toPayment(paymentRequest);
        payment.setUser(user);

        // 4. CartItemDto 리스트를 PaymentItem 리스트로 변환
        List<PaymentItem> paymentItems = cartItems.stream()
                .map(paymentMapper::toPaymentItem)  // CartItemDto -> PaymentItem 변환
                .collect(Collectors.toList());

        payment.setPaymentItems(paymentItems);

        // 5. 결제 데이터 저장
        return paymentRepository.save(payment);
    }
}
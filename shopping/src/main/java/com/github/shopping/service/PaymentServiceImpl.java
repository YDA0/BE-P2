package com.github.shopping.service;

import com.github.shopping.dto.PaymentRequestDto;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    public void processPayment(PaymentRequestDto paymentRequestDto) {
        // 결제 정보 유효성 검증 로직
        // 여기서는 외부 결제 API 호출 로직이나 간단한 검증만 넣을 수 있음.
        if (paymentRequestDto.getCardNumber().length() != 16) {
            throw new IllegalArgumentException("유효하지 않은 카드 번호입니다.");
        }

        // 여기서 실제 결제 로직을 호출할 수 있지만,
        // 지금은 단순히 "결제가 성공적으로 완료되었습니다" 메시지만 반환
    }
}

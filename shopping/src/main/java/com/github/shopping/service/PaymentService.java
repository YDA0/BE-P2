package com.github.shopping.service;

import com.github.shopping.dto.PaymentRequestDto;

public interface PaymentService {
    void processPayment(PaymentRequestDto paymentRequestDto);
}

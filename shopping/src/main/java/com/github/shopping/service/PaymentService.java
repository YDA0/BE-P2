package com.github.shopping.service;

import com.github.shopping.dto.PaymentRequestDto;
import com.github.shopping.entity.Payment;

public interface PaymentService {
    Payment processPayment(PaymentRequestDto paymentRequest);
}

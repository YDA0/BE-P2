package com.github.shopping.service;


import com.github.shopping.dto.UserInfoDto;
import com.github.shopping.entity.Payment;
import com.github.shopping.entity.PaymentItem;
import com.github.shopping.entity.User;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.repository.MyPageRepository;
import com.github.shopping.repository.PaymentRepository;
import com.github.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;
    private final PaymentRepository paymentRepository;

    // 이메일로 내 정보 조회
    @Override
    public UserInfoDto getUserInfo(String email) {
        // log.info("받은 이메일: {}", email); // 이메일 확인

        User user = myPageRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException
                        ( email + ": 이메일에 해당하는 회원을 찾을 수 없습니다"));

        // log.info("조회된 사용자: {}", user); // 사용자 정보 확인
        return new UserInfoDto(
                user.getName(),
                user.getAddress(),
                user.getPhoneNum(),
                user.getEmail()
        );
    }
    // 구매 상품 목록 조회
    @Override
    public List<PaymentItem> getPurchasedItems(String email) {
        User user = myPageRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(email + "이메일에 해당하는 회원을 찾을 수 없습니다."));

        List<Payment> payments = paymentRepository.findByUser_UserId(user.getUserId());

        // 결제 상품 목록 추출
        List<PaymentItem> purchasedItems = payments.stream()
                .flatMap(payment -> payment.getPaymentItems().stream())
                .toList();

        return purchasedItems;
    }


}

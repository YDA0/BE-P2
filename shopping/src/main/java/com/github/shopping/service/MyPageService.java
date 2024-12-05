package com.github.shopping.service;

import com.github.shopping.dto.UserInfoDto;
import com.github.shopping.entity.PaymentItem;

import java.util.List;

public interface MyPageService {
    UserInfoDto getUserInfo(String email); // 이메일로 유저정보 조회
    List<PaymentItem> getPurchasedItems(String email);
}

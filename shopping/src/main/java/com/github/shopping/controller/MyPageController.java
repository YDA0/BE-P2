package com.github.shopping.controller;

import com.github.shopping.dto.UserInfoDto;
import com.github.shopping.entity.PaymentItem;
import com.github.shopping.service.MyPageService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mypage")
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    // 이메일로 내 정보 조회 API
    @GetMapping("/info")  // "
    public ResponseEntity<UserInfoDto> getUserInfo(@RequestParam String email) {
        // 서비스에서 사용자 정보 조회
        UserInfoDto userInfo = myPageService.getUserInfo(email);

        // 조회된 정보 반환
        return ResponseEntity.ok(userInfo);
    }

    // 구매 상품 목록 조회
    @GetMapping("/purchased-items")
    public ResponseEntity<List<PaymentItem>> getPurchasedItems(@RequestParam String email) {
        List<PaymentItem> purchasedItems = myPageService.getPurchasedItems(email);

        return ResponseEntity.ok(purchasedItems);
     }
}
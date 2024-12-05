package com.github.shopping.controller;

import com.github.shopping.dto.CartItemDto;
import com.github.shopping.dto.UserInfoDto;
import com.github.shopping.service.CartService;
import com.github.shopping.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mypage")
@Slf4j
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    private final CartService cartService;

    // 이메일로 내 정보 조회 API
    @GetMapping("/info")  // "
    public ResponseEntity<UserInfoDto> getUserInfo(@RequestParam String email) {
        // 서비스에서 사용자 정보 조회
        UserInfoDto userInfo = myPageService.getUserInfo(email);

        // 조회된 정보 반환
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/cartList")
    public ResponseEntity<List<CartItemDto>> getCartItems() {
        List<CartItemDto> cartItems = cartService.getCartItems(); // CartService에서 장바구니 아이템 가져오기
        return ResponseEntity.ok(cartItems);
    }
    
}
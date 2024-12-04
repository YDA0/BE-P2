package com.github.shopping.controller;

import com.github.shopping.dto.UserInfoDto;
import com.github.shopping.service.MyPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

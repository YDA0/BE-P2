package com.github.shopping.controller;

import com.github.shopping.model.User;
import com.github.shopping.security.TokenBlacklist;
import com.github.shopping.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final TokenBlacklist tokenBlacklist;

    public AuthController(UserService userService, TokenBlacklist tokenBlacklist) {
        this.userService = userService;
        this.tokenBlacklist = tokenBlacklist;
    }

    // 회원가입 요청 처리 메서드
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user); // UserService 통해 사용자 등록
        return ResponseEntity.status(HttpStatus.CREATED) // HTTP 상태 코드 201(CREATED) 반환
                .body("회원가입 성공");
    }

    // 로그인 요청 처리 메서드
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token); // 로그인 성공 시 JWT 토큰 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 로그인 실패 시
                    .body("로그인 실패");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // HTTP 요청에서 JWT 토큰을 추출
        String token = getTokenFromRequest(request);

        if (token != null && !tokenBlacklist.isBlacklisted(token)) {
            // 토큰을 블랙리스트에 추가하여 로그아웃 처리
            tokenBlacklist.blacklistToken(token);
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 토큰");
        }
    }

    // HTTP 요청에서 JWT 토큰을 추출하는 메서드
    private
    String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 계정 삭제 요청 처리 메서드
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestParam Long userId) {
        userService.deleteAccount(userId); // UserService 통해 계정 삭제 처리
        return ResponseEntity.ok("계정 삭제 성공");
    }
}
package com.github.shopping.controller;

import com.github.shopping.dto.LoginRequest;
import com.github.shopping.exceptions.CAuthenticationEntryPointException;
import com.github.shopping.exceptions.InvalidValueException;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.entity.User;
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
        try {
            userService.register(user); // 회원가입 처리
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (InvalidValueException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // 로그인 요청 처리 메서드
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            if (token != null) {
                return ResponseEntity.ok(token);
            } else {
                throw new CAuthenticationEntryPointException("잘못된 인증 정보입니다.");
            }
        } catch (CAuthenticationEntryPointException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
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
        try {
            User user = userService.findByUserId(userId);
            if (user == null) {
                throw new NotFoundException("사용자를 찾을 수 없습니다.");
            }
            userService.deleteAccount(userId);
            return ResponseEntity.ok("계정 삭제 성공");
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
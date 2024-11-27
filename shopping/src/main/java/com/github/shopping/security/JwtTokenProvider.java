package com.github.shopping.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    // JWT 토큰 생성 메서드
    public String generateToken(String email) {
        Date now = new Date(); // 현재 시간
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());  // 만료 시간(현재 시간 + 설정된 만료 시간) 설정

        // 서명을 위한 secret key를 키 객체로 변환
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()); // HMAC SHA 알고리즘을 사용하여 비밀 키를 바이트 배열로 변환

        // JWT 토큰을 생성하는 과정
        return Jwts.builder()
                .setSubject(email)  // 토큰의 주체 설정
                .setIssuedAt(now)  // 토큰 발급 시간 설정
                .setExpiration(expiryDate)  // 토큰 만료 시간 설정
                .signWith(key)  // 서명 알고리즘과 secret 키 사용하여 JWT 서명
                .compact();  // JWT 토큰을 문자열 형태로 생성하고 반환
    }

    // JWT 토큰 유효성 검사 메서드
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

            // JWT 토큰을 파싱하여 서명 검증을 수행
            Jwts.parserBuilder()
                    .setSigningKey(key)  // 서명 검증에 사용할 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 검증하고 유효성 검사
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 토큰에서 사용자 이메일 추출
    public String getEmailFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        // 토큰에서 이메일(주체)을 추출
        return Jwts.parserBuilder()
                .setSigningKey(key)  // 서명 검증에 사용할 키 설정
                .build()
                .parseClaimsJws(token) // 토큰을 파싱하여 클레임을 추출
                .getBody()
                .getSubject();  // 이메일(주체)을 반환
    }
}
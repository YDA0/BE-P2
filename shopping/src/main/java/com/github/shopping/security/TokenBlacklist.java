package com.github.shopping.security;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklist {
    // 동기화된 HashSet 사용하여 블랙리스트 관리
    private final Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>()) ;

    // 토큰을 블랙리스트에 추가
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    // 토큰이 블랙리스트에 있는지 확인
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
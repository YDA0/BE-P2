package com.github.shopping.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*") // 모든 요청에 대해 필터 적용
public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklist tokenBlacklist;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklist = new TokenBlacklist();
    }

    // 필터 초기화 작업
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 작업이 필요하면 구현
    }

    // HTTP 요청을 처리하는 필터 메서드
    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // ServletRequest과 ServletResponse를 HTTP 요청과 응답으로 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Authorization 헤더에서 JWT 토큰을 가져오기
        String token = getTokenFromRequest(httpRequest);

        // JWT 토큰이 존재하는 경우 처리
        if (token != null) {
            // 블랙리스트에 토큰이 있는지 확인
            if (tokenBlacklist.isBlacklisted(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("");
                return; // 요청 차단
            }

            // JWT 토큰이 유효한 경우 사용자 인증 처리
            if (jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmailFromToken(token);

                // 사용자 인증 정보 생성
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, null);

                // 스프링 시큐리티 컨텍스트에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }

    // 필터 종료 시 필요한 작업이 있을 경우 구현
    @Override
    public void destroy() {
        // 종료 작업이 필요하면 구현
    }

    // HTTP 요청에서 JWT 토큰을 추출하는 메서드
    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
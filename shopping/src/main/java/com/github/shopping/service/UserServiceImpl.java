package com.github.shopping.service;


import com.github.shopping.entity.Product;
import com.github.shopping.exceptions.NotFoundException;
import com.github.shopping.entity.Roles;
import com.github.shopping.entity.User;
import com.github.shopping.entity.UserPrincipal;
import com.github.shopping.repository.ProductRepository;
import com.github.shopping.repository.UserPrincipalRepository;
import com.github.shopping.repository.UserRepository;
import com.github.shopping.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPrincipalRepository userPrincipalRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepository userRepository, UserPrincipalRepository userPrincipalRepository, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.userPrincipalRepository = userPrincipalRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }

    public boolean isValidPassword(String password) {
        System.out.println("입력된 비밀번호: " + password);
        // 비밀번호가 영문자와 숫자를 포함하고, 8자 이상 20자 이하인지 확인
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$");
    }

    @Override
    @Transactional
    public void register(User user) {
//        // 비밀번호 유효성 검증
        if (!isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 영문자와 숫자를 포함하여 8자 이상 20자 이하로 작성해야 합니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // User 저장
        userRepository.save(user);

        // 기본 역할 'USER' 부여
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUser(user);
        userPrincipal.setRole(new Roles(2L, "USER")); // 기본 역할 'USER' 설정 (role_id는 2)
        userPrincipalRepository.save(userPrincipal);
    }

    @Override
    public String login(String email, String password) {
        // 이메일로 사용자 찾기
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return null;  // 사용자 없으면 로그인 실패
        }

        User user = userOptional.get();

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;  // 비밀번호 불일치
        }

        // JWT 토큰 생성
        return jwtTokenProvider.generateToken(user.getEmail());
    }

    @Override
    @Transactional
    public void deleteAccount(Long userId) {
        // UserPrincipal 먼저 찾고 삭제
        Optional<UserPrincipal> userPrincipalOptional = userPrincipalRepository.findByUser_UserId(userId);
        if (userPrincipalOptional.isPresent()) {
            userPrincipalRepository.deleteByUser_UserId(userId);  // UserPrincipal 삭제
        }

        // User 삭제
        userRepository.deleteById(userId);
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }
}
package com.github.shopping.service;

import com.github.shopping.model.Roles;
import com.github.shopping.model.User;
import com.github.shopping.model.UserPrincipal;
import com.github.shopping.repository.UserPrincipalRepository;
import com.github.shopping.repository.UserRepository;
import com.github.shopping.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPrincipalRepository userPrincipalRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserPrincipalRepository userPrincipalRepository,
                           JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userPrincipalRepository = userPrincipalRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(User user) {
//        // 비밀번호 유효성 검증
//        if (!isValidPassword(user.getPassword()))

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
}
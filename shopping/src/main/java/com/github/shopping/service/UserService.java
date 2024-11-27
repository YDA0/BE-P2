package com.github.shopping.service;

import com.github.shopping.model.User;

public interface UserService {

    void register(User user);  // 회원가입

    String login(String email, String password);  // 로그인

    void deleteAccount(Long userId);  // 회원 탈퇴
}
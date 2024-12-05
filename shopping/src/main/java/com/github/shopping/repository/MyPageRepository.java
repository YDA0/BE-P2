package com.github.shopping.repository;

import com.github.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyPageRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // 이메일로 사용자 조회
}
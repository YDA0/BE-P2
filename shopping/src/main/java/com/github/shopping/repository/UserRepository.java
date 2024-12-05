package com.github.shopping.repository;

import com.github.shopping.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // 이메일로 사용자 조회

    boolean existsByEmail(@NotEmpty(message = "이메일을 입력해주세요") @Email(message = "유효한 이메일 형식이 아닙니다") @Size(max = 50, message = "이메일은 최대 50자까지 입력 가능합니다") String email);
}
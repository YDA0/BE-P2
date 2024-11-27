package com.github.shopping.repository;

import com.github.shopping.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPrincipalRepository extends JpaRepository<UserPrincipal, Long> {

    // user_id로 UserPrincipal 찾기
    Optional<UserPrincipal> findByUser_UserId(Long userId);

    // user_id로 UserPrincipal 삭제하기
    void deleteByUser_UserId(Long userId);
}

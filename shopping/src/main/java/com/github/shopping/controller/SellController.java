package com.github.shopping.controller;

import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.Product;
import com.github.shopping.entity.User;
import com.github.shopping.repository.UserRepository;
import com.github.shopping.service.SellServiceImpl;
import com.github.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
public class SellController {

    private final SellServiceImpl sellService;
    private final UserRepository userRepository;

    // 상품 등록 (등록과 동시에 판매)
    @PostMapping
    public ResponseEntity<SellDto> registerSell(@Validated @RequestBody SellDto sellDto, Principal principal) throws IllegalAccessException {
        // 로그인된 사용자 ID 확인
        Long userId = getUserIdFromPrincipal(principal);

        // 실제 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 상품 등록 (등록과 동시에 판매)
        Product product = new Product();
        product.setTitle(sellDto.getSellName());
        product.setPrice(sellDto.getSellPrice().intValue());
        product.setProductStock(sellDto.getSellStock());
        product.setProductImageUrl(sellDto.getSellImage());
        product.setUserId(user); // 실제 사용자 객체를 설정
        product.setProductStatus("O");

        // 상품 등록 후 판매 정보 생성
        SellDto registeredSell = sellService.registerSell(userId, product, sellDto);
        return ResponseEntity.ok(registeredSell); // 등록된 판매 정보 반환
    }

    // 판매 수정
    @PutMapping("/{sellId}")
    public ResponseEntity<SellDto> updateSell(@PathVariable Long sellId, @RequestBody SellDto sellDto, Principal principal) throws IllegalAccessException {
        // 로그인된 사용자 ID 확인
        Long userId = getUserIdFromPrincipal(principal);

        // 수정된 판매 정보 처리
        SellDto updatedSell = sellService.updateSell(sellId, sellDto, userId);
        return ResponseEntity.ok(updatedSell); // 수정된 판매 정보 반환
    }

    // 판매 삭제
    @DeleteMapping("/{sellId}")
    public ResponseEntity<Void> deleteSell(@PathVariable Long sellId, Principal principal) throws IllegalAccessException {
        // 로그인된 사용자 ID 확인
        Long userId = getUserIdFromPrincipal(principal);

        // 판매 삭제 요청 처리
        sellService.deleteSell(sellId, userId);
        return ResponseEntity.noContent().build(); // 삭제 완료 응답
    }

    // 모든 판매 목록 조회
    @GetMapping
    public ResponseEntity<List<SellDto>> getAllSells() {
        // 모든 판매 목록을 조회하여 반환
        List<SellDto> sellDtos = sellService.getAllSells();
        return ResponseEntity.ok(sellDtos); // 판매 목록 반환
    }

    // 로그인된 사용자 ID 추출
    private Long getUserIdFromPrincipal(Principal principal) {
        String username = principal.getName();  // JWT 토큰에서 가져온 이메일
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getUserId(); // 실제 사용자 ID 반환
    }
}

package com.github.shopping.controller;

import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.Product;
import com.github.shopping.entity.User;
import com.github.shopping.repository.UserRepository;
import com.github.shopping.service.SellServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    // 상품 등록
    @PostMapping
    public ResponseEntity<SellDto> registerSell(@Validated @RequestBody SellDto sellDto, Principal principal) {
        // 로그인된 사용자 ID 확인
        Long userId = getUserIdFromPrincipal(principal);

        // 실제 사용자 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Product product = new Product();
        product.setTitle(sellDto.getSellName());
        product.setPrice(sellDto.getSellPrice().intValue());
        product.setProductStock(sellDto.getSellStock());
        product.setProductImageUrl(sellDto.getSellImage());
        product.setUserId(user);

        // 상품 등록 후 판매 정보 생성
        SellDto registeredSell = sellService.registerSell(userId, product, sellDto);
        return ResponseEntity.ok(registeredSell);
    }

    // 상품 수정
    @PutMapping("/{sellId}")
    public ResponseEntity<SellDto> updateSell(@PathVariable Long sellId, @RequestBody SellDto sellDto, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        SellDto updatedSell = sellService.updateSell(userId, sellId, sellDto);
        return ResponseEntity.ok(updatedSell);
    }

    // TODO: 판매자 상품 전체 조회
    @GetMapping
    public ResponseEntity<List<SellDto>> getAllSells() {
        List<SellDto> sellDtos = sellService.getAllSells();
        return ResponseEntity.ok(sellDtos);
    }

    // 로그인된 사용자 ID 추출
    private Long getUserIdFromPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getUserId();
    }
}

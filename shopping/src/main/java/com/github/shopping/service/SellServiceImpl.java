package com.github.shopping.service;

import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.*;
import com.github.shopping.repository.ProductRepository;
import com.github.shopping.repository.RolesRepository;
import com.github.shopping.repository.SellRepository;
import com.github.shopping.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellServiceImpl implements SellService {
    private final SellRepository sellRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final ProductRepository productRepository;

    // 판매 등록 (상품과 판매 등록)
    @Transactional
    @Override
    public SellDto registerSell(Long userId, Product product, SellDto sellDto) throws IllegalAccessException {
        // 사용자 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalAccessException("User not found"));

        // 상품 등록
        product.setUserId(user);  // 상품 등록한 사용자 설정
        Product savedProduct = productRepository.save(product);  // 상품 저장

        // 판매 정보 등록
        Sell sell = Sell.builder()
                .userId(user)
                .product(savedProduct)  // 저장된 상품과 연결
                .sellName(sellDto.getSellName())
                .sellStock(sellDto.getSellStock())
                .sellPrice(sellDto.getSellPrice())
                .sellImage(sellDto.getSellImage())
                .build();

        Sell savedSell = sellRepository.save(sell); // 판매 정보 저장

        return convertToResponseDto(savedSell);  // 등록된 판매 정보를 반환
    }

    // 판매 수정
    @Transactional
    @Override
    public SellDto updateSell(Long sellId, SellDto sellDto, Long userId) throws IllegalAccessException {
        Sell sell = sellRepository.findById(sellId)
                .orElseThrow(() -> new IllegalAccessException("Sell not found"));

        // 판매자가 아닐 경우 예외 처리
        if (!sell.getUserId().getUserId().equals(userId)) {
            throw new IllegalAccessException("Only the owner can update this product.");
        }

        // 수정할 값 업데이트
        sell.setSellName(sellDto.getSellName());
        sell.setSellPrice(sellDto.getSellPrice());
        sell.setSellStock(sellDto.getSellStock());
        sell.setSellImage(sellDto.getSellImage());
        sellRepository.save(sell);

        return convertToResponseDto(sell);  // 수정된 판매 정보를 반환
    }

    // 판매 삭제
    @Transactional
    @Override
    public void deleteSell(Long sellId, Long userId) throws IllegalAccessException {
        Sell sell = sellRepository.findById(sellId)
                .orElseThrow(() -> new IllegalAccessException("Sell not found"));

        // 판매자가 아닐 경우 예외 처리
        if (!sell.getUserId().getUserId().equals(userId)) {
            throw new IllegalAccessException("Only the owner can delete this product.");
        }

        // 판매 삭제
        sellRepository.delete(sell);
    }

    // 모든 판매 조회
    @Override
    public List<SellDto> getAllSells() {
        return sellRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Sell 엔티티를 SellDto로 변환
    private SellDto convertToResponseDto(Sell sell) {
        SellDto dto = new SellDto();
        dto.setSellId(sell.getSellId());
        dto.setUserId(sell.getUserId().getUserId());
        dto.setSellName(sell.getSellName());
        dto.setSellStock(sell.getSellStock());
        dto.setSellPrice(sell.getSellPrice());
        dto.setSellImage(sell.getSellImage());
        dto.setSellCreateAt(sell.getSellCreateAt());
        dto.setSellUpdateAt(sell.getSellUpdateAt() != null ? sell.getSellUpdateAt() : null);
        return dto;
    }
}

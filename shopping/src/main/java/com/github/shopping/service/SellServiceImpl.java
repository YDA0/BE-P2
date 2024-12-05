package com.github.shopping.service;

import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.Product;
import com.github.shopping.entity.Sell;
import com.github.shopping.entity.User;
import com.github.shopping.repository.ProductRepository;
import com.github.shopping.repository.SellRepository;
import com.github.shopping.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellServiceImpl implements SellService {
    private final SellRepository sellRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 상품 등록
    @Override
    @Transactional
    public SellDto registerSell(Long userId, Product product, SellDto sellDto) {
        // 사용자 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 판매 종료일자 필수 확인
        if (sellDto.getSellEndDate() == null) {
            throw new RuntimeException("판매 종료일자는 필수 입력 항목입니다.");
        }

        product.setUserId(user); // 상품 등록한 사용자 설정
        Product savedProduct = productRepository.save(product); // 상품 저장

        // 판매 정보 등록
        Sell sell = Sell.builder()
                .userId(user)
                .product(savedProduct)
                .sellName(sellDto.getSellName())
                .sellPrice(sellDto.getSellPrice())
                .sellStock(sellDto.getSellStock())
                .sellImage(sellDto.getSellImage())
                .sellContents(sellDto.getSellContents())
                .sellEndDate(sellDto.getSellEndDate())
                .build();

        // 판매 정보 저장
        Sell savedSell = sellRepository.save(sell);
        // 등록된 판매 정보 반환
        return convertToResponseDto(savedSell);
    }

    // 상품 수정
    public SellDto updateSell(Long userId, Long sellId, SellDto sellDto) {
        Sell sell = sellRepository.findById(sellId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        // 판매자가 아닐 경우 예외 처리
        if (!sell.getUserId().getUserId().equals(userId)) {
            throw new RuntimeException("상품을 등록한 판매자만 수정 가능합니다.");
        }

        // 수정할 값 업데이트
        sell.setSellName(sellDto.getSellName());
        sell.setSellPrice(sellDto.getSellPrice());
        sell.setSellStock(sellDto.getSellStock());
        sell.setSellImage(sellDto.getSellImage());
        sell.setSellContents(sellDto.getSellContents());
        sellRepository.save(sell);

        return convertToResponseDto(sell);
    }

    // TODO: 판매자 상품 전체 조회
    @Override
    public List<SellDto> getAllSells() {
        return sellRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Sell 엔티티를 SellDto로 변환
    private SellDto convertToResponseDto(Sell sell){
        SellDto dto = new SellDto();
        dto.setSellId(sell.getSellId());
        dto.setUserId(sell.getUserId().getUserId());
        dto.setSellName(sell.getSellName());
        dto.setSellStock(sell.getSellStock());
        dto.setSellPrice(sell.getSellPrice());
        dto.setSellImage(sell.getSellImage());
        dto.setSellContents(sell.getSellContents());
        dto.setSellCreateAt(sell.getSellCreateAt());
        dto.setSellUpdateAt(sell.getSellUpdateAt() != null ? sell.getSellUpdateAt() : null);
        dto.setSellEndDate(sell.getSellEndDate());
        return dto;
    }
}

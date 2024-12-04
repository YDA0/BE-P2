package com.github.shopping.service;

import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.Product;

import java.util.List;

public interface SellService {
    // 상품과 판매 정보를 등록하는 메서드
    SellDto registerSell(Long userId, Product product, SellDto sellDto);

    List<SellDto> getAllSells();
}

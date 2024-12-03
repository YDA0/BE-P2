package com.github.shopping.service;

import com.github.shopping.dto.SellDto;
import com.github.shopping.entity.Product;

import java.util.List;

public interface SellService {
    // 상품과 판매 정보를 등록하는 메서드
    SellDto registerSell(Long userId, Product product, SellDto sellDto) throws IllegalAccessException;

    // 판매 정보를 수정하는 메서드
    SellDto updateSell(Long sellId, SellDto sellDto, Long userId) throws IllegalAccessException;

    // 판매 정보를 삭제하는 메서드
    void deleteSell(Long sellId, Long userId) throws IllegalAccessException;

    // 모든 판매 정보를 조회하는 메서드
    List<SellDto> getAllSells();
}
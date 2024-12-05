package com.github.shopping.service;

import com.github.shopping.dto.AddToCartRequestDto;
import com.github.shopping.entity.Cart;
import com.github.shopping.mapper.CartMapper;
import com.github.shopping.entity.Product;
import com.github.shopping.mapper.ProductMapper;
import com.github.shopping.repository.CartRepository;
import com.github.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartMapper cartMapper;

    private final ProductService productService;

    // 장바구니 항목 추가 메서드
    @Transactional
    public Cart addToCart(AddToCartRequestDto dto) {
        // 상품 조회 및 재고 수량 확인
        Product product = productService.checkAndUpdateProductStock(dto.getProductId(), dto.getQuantity());

        // AddToCartRequestDto -> Cart 변환
        Cart cart = cartMapper.toCart(dto, product);

        // 장바구니에 저장
        return cartRepository.save(cart);  // Cart 객체 반환
    }

    @Transactional
    public Cart updateQuantity(Long cartId, int changeAmount) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니 항목을 찾을 수 없습니다."));

        // 현재 수량에 증감량을 적용
        int newQuantity = cart.getQuantity() + changeAmount;

        // 수량이 1보다 작을 수 없도록 처리
        if (newQuantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수량은 최소 1개 이상이어야 합니다.");
        }

        // 수량 업데이트 및 가격 계산
        cart.setQuantity(newQuantity);
        cart.setCartPrice(cart.getProduct().getPrice() * newQuantity);

        return cartRepository.save(cart);  // 변경된 장바구니 항목 반환
    }
}


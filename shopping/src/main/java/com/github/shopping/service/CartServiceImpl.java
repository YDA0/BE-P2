package com.github.shopping.service;

import com.github.shopping.dto.AddToCartRequestDto;
import com.github.shopping.dto.CartItemDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final CartMapper cartMapper;

    private final ProductService productService;




    @Override
    @Transactional
    public Cart addToCart(AddToCartRequestDto dto) {
        Product product = productService.findById(dto.getProductId());

        // 상품의 재고를 차감 (checkAndUpdateProductStock 메서드를 사용)
        productService.checkAndUpdateProductStock(product.getProductId(), dto.getQuantity());

        // 장바구니에서 해당 상품이 있는지 확인
        Optional<Cart> existingCart = cartRepository.findByProductAndColorOptionAndSizeOption(product, dto.getSelectedColor(), dto.getSelectedSize());

        if (existingCart.isPresent()) {
            // 상품이 이미 장바구니에 있는 경우, 수량만 업데이트
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + dto.getQuantity());
            cart.setCartPrice(cart.getCartPrice() + (dto.getPrice() * dto.getQuantity()));

            // 장바구니 항목 업데이트
            return cartRepository.save(cart);
        } else {
            // 상품이 없으면 새로 추가
            Cart cart = cartMapper.toCart(dto, product);

            // 장바구니에 새 항목 추가
            return cartRepository.save(cart);
        }
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

    @Override
    public List<CartItemDto> getCartItems() {
        // Cart 엔티티 리스트를 가져옴
        List<Cart> cartList = cartRepository.findAll();

        // 매퍼를 사용해 Cart 리스트를 CartItemDto 리스트로 변환
        return cartList.stream()
                .map(cartMapper::toCartItemDto) // MapStruct 매퍼 사용
                .collect(Collectors.toList());
    }

}


package com.github.shopping.mapper;

import com.github.shopping.dto.CartItemDto;
import com.github.shopping.dto.PaymentRequestDto;
import com.github.shopping.entity.Payment;
import com.github.shopping.entity.PaymentItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "paymentEmail", source = "paymentRequestDto.email")
    @Mapping(target = "cardNumber", source = "paymentRequestDto.cardNumber")
    @Mapping(target = "expiryDate", source = "paymentRequestDto.expiryDate")
    @Mapping(target = "cvc", source = "paymentRequestDto.cvc")
    @Mapping(target = "address", source = "paymentRequestDto.address")
    @Mapping(target = "zipCode", source = "paymentRequestDto.zipCode")
    Payment toPayment(PaymentRequestDto paymentRequestDto);




    @Mapping(target = "paymentItemTitle", source = "cartItemDto.title")
    @Mapping(target = "quantity", source = "cartItemDto.quantity")
    @Mapping(target = "paymentItemPrice", source = "cartItemDto.cartPrice")
    @Mapping(target = "colorOption", source = "cartItemDto.colorOption")
    @Mapping(target = "sizeOption", source = "cartItemDto.sizeOption")
    PaymentItem toPaymentItem(CartItemDto cartItemDto);

}

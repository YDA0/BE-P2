package com.github.shopping.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentRequestDto {

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email; // 사용자 이메일

    @NotBlank(message = "카드 번호는 필수 입력 항목입니다.")
    private String cardNumber; // 카드 번호

    @NotBlank(message = "유효기간은 필수 입력 항목입니다.")
    private String expiryDate; // 카드 유효기간 (MM/YY)

    @NotBlank(message = "CVC는 필수 입력 항목입니다.")
    private String cvc; // 카드 CVC 번호

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String address; // 배송 주소

    @NotBlank(message = "우편번호는 필수 입력 항목입니다.")
    private String postalCode; // 우편번호

}
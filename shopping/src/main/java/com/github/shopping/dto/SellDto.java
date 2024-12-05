package com.github.shopping.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellDto {
    private Long sellId;
    private Long userId;
    private String sellName;
    private Integer sellStock;
    private Double sellPrice;
    private String sellImage;
    private String sellContents;
    private LocalDateTime sellCreateAt;
    private LocalDateTime sellUpdateAt;

    @NotNull(message = "판매 종료일자는 필수 입력 항목입니다.")
    @Future(message = "오늘 이후의 날짜만 입력 가능합니다.")
    private LocalDate sellEndDate;
}

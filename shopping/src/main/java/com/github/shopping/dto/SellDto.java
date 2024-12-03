package com.github.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDateTime sellCreateAt;
    private LocalDateTime sellUpdateAt;
}

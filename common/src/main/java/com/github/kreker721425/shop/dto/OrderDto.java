package com.github.kreker721425.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal price;
    private BigDecimal bonusCount;
    private ClientDto client;
    private UserDto user;
    private List<ProductOrderDto> products;
}

package com.github.kreker721425.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductOrderDto {
    private Long id;
    private ProductDto product;
    private Long count;
}

package com.github.kreker721425.shop.repository.filter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ProductOrderFilter extends Filter {
    private String id;
    private String productId;
    private String orderId;
    private String count;
}
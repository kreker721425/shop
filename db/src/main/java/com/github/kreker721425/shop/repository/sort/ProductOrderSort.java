package com.github.kreker721425.shop.repository.sort;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductOrderSort extends Sort {
    private Boolean productId;
    private Boolean orderId;
    private Boolean count;
}
package com.github.kreker721425.shop.repository.sort;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderSort extends Sort {
    private Boolean id;
    private Boolean price;
    private Boolean createdAt;
    private Boolean clientName;
    private Boolean bonusCount;
}
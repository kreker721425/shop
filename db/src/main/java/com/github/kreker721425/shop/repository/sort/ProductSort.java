package com.github.kreker721425.shop.repository.sort;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductSort extends Sort {
    private Boolean id;
    private Boolean article;
    private Boolean name;
    private Boolean count;
    private Boolean description;
    private Boolean price;
    private Boolean priceDiscount;
}
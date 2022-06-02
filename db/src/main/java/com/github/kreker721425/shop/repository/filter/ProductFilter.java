package com.github.kreker721425.shop.repository.filter;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ProductFilter extends Filter {
    private String id;
    private String name;
    private String count;
    private String description;
    private String price;
    private String priceDiscount;
}
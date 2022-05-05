package com.github.kreker721425.shop.repository.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductOrderFilter extends Filter {
    private List<String> productId = new ArrayList<>();
    private List<String> orderId = new ArrayList<>();
    private List<String> count = new ArrayList<>();
}
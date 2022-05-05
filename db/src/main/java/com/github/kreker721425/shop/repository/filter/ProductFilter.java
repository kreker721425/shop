package com.github.kreker721425.shop.repository.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductFilter extends Filter {
    private List<String> id = new ArrayList<>();
    private List<String> article = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> count = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> price = new ArrayList<>();
    private List<String> priceDiscount = new ArrayList<>();
}
package com.github.kreker721425.shop.service;

import com.github.kreker721425.shop.dto.ProductDto;
import com.github.kreker721425.shop.repository.filter.ProductFilter;

import java.util.List;

public interface ProductService extends CRUDService<ProductDto, Long, ProductFilter> {

    void updateCount(List<ProductDto> products);
}

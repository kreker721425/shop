package com.github.kreker721425.shop.service;

import com.github.kreker721425.shop.dto.ProductOrderDto;
import com.github.kreker721425.shop.repository.filter.ProductOrderFilter;

import java.util.List;

public interface ProductOrderService extends CRUDService<ProductOrderDto, Long, ProductOrderFilter> {

    List<ProductOrderDto> getByOrderId(Long orderId);
}

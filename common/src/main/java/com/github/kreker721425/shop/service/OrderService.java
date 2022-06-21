package com.github.kreker721425.shop.service;

import com.github.kreker721425.shop.dto.OrderDto;
import com.github.kreker721425.shop.repository.filter.OrderFilter;

public interface OrderService extends CRUDService<OrderDto, Long, OrderFilter> {
}

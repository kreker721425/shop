package com.github.kreker721425.shop.service;

import com.github.kreker721425.shop.dto.ClientDto;
import com.github.kreker721425.shop.repository.filter.ClientFilter;

import java.math.BigDecimal;

public interface ClientService extends CRUDService<ClientDto, Long, ClientFilter> {

    ClientDto getByPhone(String phone);

    void updateBonusCount(Long id, BigDecimal bonusCount);
}

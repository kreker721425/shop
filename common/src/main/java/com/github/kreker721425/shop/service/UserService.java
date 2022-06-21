package com.github.kreker721425.shop.service;

import com.github.kreker721425.shop.dto.UserDto;
import com.github.kreker721425.shop.repository.filter.Filter;
import com.github.kreker721425.shop.repository.filter.UserFilter;

public interface UserService extends CRUDService<UserDto, Long, UserFilter> {

    UserDto findUserByLogin(String login);

    UserDto activateUser(Long id);

    UserDto deactivateUser(Long id);
}

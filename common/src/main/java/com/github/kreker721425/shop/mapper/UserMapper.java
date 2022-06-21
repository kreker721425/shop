package com.github.kreker721425.shop.mapper;

import com.github.kreker721425.shop.db.tables.pojos.Users;
import com.github.kreker721425.shop.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(Users client);

    List<UserDto> map(List<Users> clients);

    Users mapToEntity(UserDto dto);
}

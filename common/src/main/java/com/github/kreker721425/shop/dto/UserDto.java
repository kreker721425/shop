package com.github.kreker721425.shop.dto;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.db.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String name;
    private UserRoleEnum role;
    private UserStatusEnum status;
}

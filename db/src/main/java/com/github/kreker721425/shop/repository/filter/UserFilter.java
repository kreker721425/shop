package com.github.kreker721425.shop.repository.filter;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.db.enums.UserStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
public class UserFilter extends Filter {
    private String id;
    private String name;
    private String login;
    private UserRoleEnum role;
    private UserStatusEnum status;
}

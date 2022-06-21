package com.github.kreker721425.shop.security;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.db.enums.UserStatusEnum;
import com.github.kreker721425.shop.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private String login;
    private String password;
    private Boolean enabled;
    private UserRoleEnum role;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUsersToCustomUserDetails(UserDto user) {
        CustomUserDetails c = new CustomUserDetails();
        c.login = user.getLogin();
        c.password = user.getPassword();
        c.enabled = user.getStatus() == UserStatusEnum.ACTIVE;
        c.role = user.getRole();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_".concat(user.getRole().getLiteral())));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UserRoleEnum getRole() {
        return role;
    }
}

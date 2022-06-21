package com.github.kreker721425.shop.security;

import com.github.kreker721425.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        var user = userService.findUserByLogin(s);
        if (user != null && user.getRole() != null) {
            return CustomUserDetails.fromUsersToCustomUserDetails(user);
        }
        return null;
    }

}

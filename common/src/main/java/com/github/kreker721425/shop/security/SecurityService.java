package com.github.kreker721425.shop.security;

import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.dto.UserDto;
import com.github.kreker721425.shop.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private final UserService userService;

    public SecurityService(@Lazy UserService userService) {
        this.userService = userService;
    }

    private static final String LOGOUT_SUCCESS_URL = "/";

    private CustomUserDetails getAuthenticatedCustomUser() {
        var context = SecurityContextHolder.getContext();
        var principal = context.getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails;
        }
        throw new SecurityException("User not authorized");
    }

    public UserDto getAuthenticatedUser() {
        return userService.findUserByLogin(getAuthenticatedCustomUser().getUsername());
    }

    public UserRoleEnum getAuthenticatedUserRole() {
        return getAuthenticatedCustomUser().getRole();
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        var logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}

/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.pojos;


import com.github.kreker721425.shop.db.enums.UserRoleEnum;
import com.github.kreker721425.shop.db.enums.UserStatusEnum;

import java.io.Serializable;


/**
 * Пользователь
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long           id;
    private String         name;
    private String         login;
    private String         password;
    private UserRoleEnum   role;
    private UserStatusEnum status;

    public Users() {}

    public Users(Users value) {
        this.id = value.id;
        this.name = value.name;
        this.login = value.login;
        this.password = value.password;
        this.role = value.role;
        this.status = value.status;
    }

    public Users(
        Long           id,
        String         name,
        String         login,
        String         password,
        UserRoleEnum   role,
        UserStatusEnum status
    ) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    /**
     * Getter for <code>shop.users.id</code>. Идентификатор
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>shop.users.id</code>. Идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>shop.users.name</code>. Имя
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>shop.users.name</code>. Имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>shop.users.login</code>. Логин
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Setter for <code>shop.users.login</code>. Логин
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter for <code>shop.users.password</code>. Пароль
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>shop.users.password</code>. Пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for <code>shop.users.role</code>. Роль
     */
    public UserRoleEnum getRole() {
        return this.role;
    }

    /**
     * Setter for <code>shop.users.role</code>. Роль
     */
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    /**
     * Getter for <code>shop.users.status</code>. Статус
     */
    public UserStatusEnum getStatus() {
        return this.status;
    }

    /**
     * Setter for <code>shop.users.status</code>. Статус
     */
    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Users (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(login);
        sb.append(", ").append(password);
        sb.append(", ").append(role);
        sb.append(", ").append(status);

        sb.append(")");
        return sb.toString();
    }
}

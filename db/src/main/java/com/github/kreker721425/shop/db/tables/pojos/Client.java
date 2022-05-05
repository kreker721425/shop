/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Клиенты
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long       id;
    private String     name;
    private String     phone;
    private BigDecimal bonusCount;
    private LocalDate  birthday;

    public Client() {}

    public Client(Client value) {
        this.id = value.id;
        this.name = value.name;
        this.phone = value.phone;
        this.bonusCount = value.bonusCount;
        this.birthday = value.birthday;
    }

    public Client(
        Long       id,
        String     name,
        String     phone,
        BigDecimal bonusCount,
        LocalDate  birthday
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.bonusCount = bonusCount;
        this.birthday = birthday;
    }

    /**
     * Getter for <code>shop.client.id</code>. Идентификатор клиента
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>shop.client.id</code>. Идентификатор клиента
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>shop.client.name</code>. Имя клиента
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>shop.client.name</code>. Имя клиента
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>shop.client.phone</code>. Номер телефона клиента
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Setter for <code>shop.client.phone</code>. Номер телефона клиента
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for <code>shop.client.bonus_count</code>. Количество бонусных баллов клиента
     */
    public BigDecimal getBonusCount() {
        return this.bonusCount;
    }

    /**
     * Setter for <code>shop.client.bonus_count</code>. Количество бонусных баллов клиента
     */
    public void setBonusCount(BigDecimal bonusCount) {
        this.bonusCount = bonusCount;
    }

    /**
     * Getter for <code>shop.client.birthday</code>. Дата рождения клиента
     */
    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * Setter for <code>shop.client.birthday</code>. Дата рождения клиента
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Client (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(phone);
        sb.append(", ").append(bonusCount);
        sb.append(", ").append(birthday);

        sb.append(")");
        return sb.toString();
    }
}
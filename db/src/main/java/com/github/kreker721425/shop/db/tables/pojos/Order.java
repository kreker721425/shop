/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * Заказ
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long          id;
    private LocalDateTime createdAt;
    private BigDecimal    price;
    private Long          clientId;
    private BigDecimal    bonusCount;

    public Order() {}

    public Order(Order value) {
        this.id = value.id;
        this.createdAt = value.createdAt;
        this.price = value.price;
        this.clientId = value.clientId;
        this.bonusCount = value.bonusCount;
    }

    public Order(
        Long          id,
        LocalDateTime createdAt,
        BigDecimal    price,
        Long          clientId,
        BigDecimal    bonusCount
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
        this.clientId = clientId;
        this.bonusCount = bonusCount;
    }

    /**
     * Getter for <code>shop.order.id</code>. Идентификатор заказа
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>shop.order.id</code>. Идентификатор заказа
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>shop.order.created_at</code>. Время создания заказа
     */
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Setter for <code>shop.order.created_at</code>. Время создания заказа
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for <code>shop.order.price</code>. Стоимость заказа
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Setter for <code>shop.order.price</code>. Стоимость заказа
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getter for <code>shop.order.client_id</code>. Идентификатор клиента заказа
     */
    public Long getClientId() {
        return this.clientId;
    }

    /**
     * Setter for <code>shop.order.client_id</code>. Идентификатор клиента заказа
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for <code>shop.order.bonus_count</code>. Количество бонусных баллов за заказ
     */
    public BigDecimal getBonusCount() {
        return this.bonusCount;
    }

    /**
     * Setter for <code>shop.order.bonus_count</code>. Количество бонусных баллов за заказ
     */
    public void setBonusCount(BigDecimal bonusCount) {
        this.bonusCount = bonusCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order (");

        sb.append(id);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(price);
        sb.append(", ").append(clientId);
        sb.append(", ").append(bonusCount);

        sb.append(")");
        return sb.toString();
    }
}
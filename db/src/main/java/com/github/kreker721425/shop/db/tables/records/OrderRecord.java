/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.records;


import com.github.kreker721425.shop.db.tables.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * Заказ
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrderRecord extends UpdatableRecordImpl<OrderRecord> implements Record5<Long, LocalDateTime, BigDecimal, Long, BigDecimal> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>shop.order.id</code>. Идентификатор
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>shop.order.id</code>. Идентификатор
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>shop.order.created_at</code>. Время создания
     */
    public void setCreatedAt(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>shop.order.created_at</code>. Время создания
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>shop.order.price</code>. Стоимость
     */
    public void setPrice(BigDecimal value) {
        set(2, value);
    }

    /**
     * Getter for <code>shop.order.price</code>. Стоимость
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(2);
    }

    /**
     * Setter for <code>shop.order.client_id</code>. Идентификатор клиента
     */
    public void setClientId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>shop.order.client_id</code>. Идентификатор клиента
     */
    public Long getClientId() {
        return (Long) get(3);
    }

    /**
     * Setter for <code>shop.order.bonus_count</code>. Количество бонусных баллов за заказ
     */
    public void setBonusCount(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>shop.order.bonus_count</code>. Количество бонусных баллов за заказ
     */
    public BigDecimal getBonusCount() {
        return (BigDecimal) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, LocalDateTime, BigDecimal, Long, BigDecimal> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, LocalDateTime, BigDecimal, Long, BigDecimal> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Order.ORDER.ID;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return Order.ORDER.CREATED_AT;
    }

    @Override
    public Field<BigDecimal> field3() {
        return Order.ORDER.PRICE;
    }

    @Override
    public Field<Long> field4() {
        return Order.ORDER.CLIENT_ID;
    }

    @Override
    public Field<BigDecimal> field5() {
        return Order.ORDER.BONUS_COUNT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public LocalDateTime component2() {
        return getCreatedAt();
    }

    @Override
    public BigDecimal component3() {
        return getPrice();
    }

    @Override
    public Long component4() {
        return getClientId();
    }

    @Override
    public BigDecimal component5() {
        return getBonusCount();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public LocalDateTime value2() {
        return getCreatedAt();
    }

    @Override
    public BigDecimal value3() {
        return getPrice();
    }

    @Override
    public Long value4() {
        return getClientId();
    }

    @Override
    public BigDecimal value5() {
        return getBonusCount();
    }

    @Override
    public OrderRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public OrderRecord value2(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public OrderRecord value3(BigDecimal value) {
        setPrice(value);
        return this;
    }

    @Override
    public OrderRecord value4(Long value) {
        setClientId(value);
        return this;
    }

    @Override
    public OrderRecord value5(BigDecimal value) {
        setBonusCount(value);
        return this;
    }

    @Override
    public OrderRecord values(Long value1, LocalDateTime value2, BigDecimal value3, Long value4, BigDecimal value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderRecord
     */
    public OrderRecord() {
        super(Order.ORDER);
    }

    /**
     * Create a detached, initialised OrderRecord
     */
    public OrderRecord(Long id, LocalDateTime createdAt, BigDecimal price, Long clientId, BigDecimal bonusCount) {
        super(Order.ORDER);

        setId(id);
        setCreatedAt(createdAt);
        setPrice(price);
        setClientId(clientId);
        setBonusCount(bonusCount);
    }
}

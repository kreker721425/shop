/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.records;


import com.github.kreker721425.shop.db.tables.ProductOrder;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.TableRecordImpl;


/**
 * Состав заказа
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductOrderRecord extends TableRecordImpl<ProductOrderRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>shop.product_order.product_id</code>. Идентификатор продукта
     */
    public void setProductId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>shop.product_order.product_id</code>. Идентификатор продукта
     */
    public Long getProductId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>shop.product_order.order_id</code>. Идентификатор заказа
     */
    public void setOrderId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>shop.product_order.order_id</code>. Идентификатор заказа
     */
    public Long getOrderId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>shop.product_order.count</code>. Количество продукта в заказе
     */
    public void setCount(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>shop.product_order.count</code>. Количество продукта в заказе
     */
    public Long getCount() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return ProductOrder.PRODUCT_ORDER.PRODUCT_ID;
    }

    @Override
    public Field<Long> field2() {
        return ProductOrder.PRODUCT_ORDER.ORDER_ID;
    }

    @Override
    public Field<Long> field3() {
        return ProductOrder.PRODUCT_ORDER.COUNT;
    }

    @Override
    public Long component1() {
        return getProductId();
    }

    @Override
    public Long component2() {
        return getOrderId();
    }

    @Override
    public Long component3() {
        return getCount();
    }

    @Override
    public Long value1() {
        return getProductId();
    }

    @Override
    public Long value2() {
        return getOrderId();
    }

    @Override
    public Long value3() {
        return getCount();
    }

    @Override
    public ProductOrderRecord value1(Long value) {
        setProductId(value);
        return this;
    }

    @Override
    public ProductOrderRecord value2(Long value) {
        setOrderId(value);
        return this;
    }

    @Override
    public ProductOrderRecord value3(Long value) {
        setCount(value);
        return this;
    }

    @Override
    public ProductOrderRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductOrderRecord
     */
    public ProductOrderRecord() {
        super(ProductOrder.PRODUCT_ORDER);
    }

    /**
     * Create a detached, initialised ProductOrderRecord
     */
    public ProductOrderRecord(Long productId, Long orderId, Long count) {
        super(ProductOrder.PRODUCT_ORDER);

        setProductId(productId);
        setOrderId(orderId);
        setCount(count);
    }
}
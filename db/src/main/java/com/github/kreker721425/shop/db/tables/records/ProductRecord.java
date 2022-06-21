/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.records;


import com.github.kreker721425.shop.db.tables.Product;

import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * Продукт
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductRecord extends UpdatableRecordImpl<ProductRecord> implements Record7<Long, String, String, Integer, String, BigDecimal, BigDecimal> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>shop.product.id</code>. Артикул
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>shop.product.id</code>. Артикул
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>shop.product.article</code>.
     */
    public void setArticle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>shop.product.article</code>.
     */
    public String getArticle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>shop.product.name</code>. Название
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>shop.product.name</code>. Название
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>shop.product.count</code>. Количество
     */
    public void setCount(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>shop.product.count</code>. Количество
     */
    public Integer getCount() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>shop.product.description</code>. Описание
     */
    public void setDescription(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>shop.product.description</code>. Описание
     */
    public String getDescription() {
        return (String) get(4);
    }

    /**
     * Setter for <code>shop.product.price</code>. Цена
     */
    public void setPrice(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>shop.product.price</code>. Цена
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>shop.product.price_discount</code>. Цена продукта со скидкой
     */
    public void setPriceDiscount(BigDecimal value) {
        set(6, value);
    }

    /**
     * Getter for <code>shop.product.price_discount</code>. Цена продукта со скидкой
     */
    public BigDecimal getPriceDiscount() {
        return (BigDecimal) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, String, Integer, String, BigDecimal, BigDecimal> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, String, String, Integer, String, BigDecimal, BigDecimal> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Product.PRODUCT.ID;
    }

    @Override
    public Field<String> field2() {
        return Product.PRODUCT.ARTICLE;
    }

    @Override
    public Field<String> field3() {
        return Product.PRODUCT.NAME;
    }

    @Override
    public Field<Integer> field4() {
        return Product.PRODUCT.COUNT;
    }

    @Override
    public Field<String> field5() {
        return Product.PRODUCT.DESCRIPTION;
    }

    @Override
    public Field<BigDecimal> field6() {
        return Product.PRODUCT.PRICE;
    }

    @Override
    public Field<BigDecimal> field7() {
        return Product.PRODUCT.PRICE_DISCOUNT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getArticle();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public Integer component4() {
        return getCount();
    }

    @Override
    public String component5() {
        return getDescription();
    }

    @Override
    public BigDecimal component6() {
        return getPrice();
    }

    @Override
    public BigDecimal component7() {
        return getPriceDiscount();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getArticle();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public Integer value4() {
        return getCount();
    }

    @Override
    public String value5() {
        return getDescription();
    }

    @Override
    public BigDecimal value6() {
        return getPrice();
    }

    @Override
    public BigDecimal value7() {
        return getPriceDiscount();
    }

    @Override
    public ProductRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public ProductRecord value2(String value) {
        setArticle(value);
        return this;
    }

    @Override
    public ProductRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public ProductRecord value4(Integer value) {
        setCount(value);
        return this;
    }

    @Override
    public ProductRecord value5(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public ProductRecord value6(BigDecimal value) {
        setPrice(value);
        return this;
    }

    @Override
    public ProductRecord value7(BigDecimal value) {
        setPriceDiscount(value);
        return this;
    }

    @Override
    public ProductRecord values(Long value1, String value2, String value3, Integer value4, String value5, BigDecimal value6, BigDecimal value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductRecord
     */
    public ProductRecord() {
        super(Product.PRODUCT);
    }

    /**
     * Create a detached, initialised ProductRecord
     */
    public ProductRecord(Long id, String article, String name, Integer count, String description, BigDecimal price, BigDecimal priceDiscount) {
        super(Product.PRODUCT);

        setId(id);
        setArticle(article);
        setName(name);
        setCount(count);
        setDescription(description);
        setPrice(price);
        setPriceDiscount(priceDiscount);
    }
}

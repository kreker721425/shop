/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.daos;


import com.github.kreker721425.shop.db.tables.Product;
import com.github.kreker721425.shop.db.tables.records.ProductRecord;

import java.math.BigDecimal;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * Продукты
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductDao extends DAOImpl<ProductRecord, com.github.kreker721425.shop.db.tables.pojos.Product, Long> {

    /**
     * Create a new ProductDao without any configuration
     */
    public ProductDao() {
        super(Product.PRODUCT, com.github.kreker721425.shop.db.tables.pojos.Product.class);
    }

    /**
     * Create a new ProductDao with an attached configuration
     */
    public ProductDao(Configuration configuration) {
        super(Product.PRODUCT, com.github.kreker721425.shop.db.tables.pojos.Product.class, configuration);
    }

    @Override
    public Long getId(com.github.kreker721425.shop.db.tables.pojos.Product object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Product.PRODUCT.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchById(Long... values) {
        return fetch(Product.PRODUCT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.github.kreker721425.shop.db.tables.pojos.Product fetchOneById(Long value) {
        return fetchOne(Product.PRODUCT.ID, value);
    }

    /**
     * Fetch records that have <code>article BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfArticle(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Product.PRODUCT.ARTICLE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>article IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchByArticle(Long... values) {
        return fetch(Product.PRODUCT.ARTICLE, values);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(Product.PRODUCT.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchByName(String... values) {
        return fetch(Product.PRODUCT.NAME, values);
    }

    /**
     * Fetch records that have <code>count BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfCount(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Product.PRODUCT.COUNT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>count IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchByCount(Long... values) {
        return fetch(Product.PRODUCT.COUNT, values);
    }

    /**
     * Fetch records that have <code>description BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfDescription(String lowerInclusive, String upperInclusive) {
        return fetchRange(Product.PRODUCT.DESCRIPTION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchByDescription(String... values) {
        return fetch(Product.PRODUCT.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>price BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfPrice(BigDecimal lowerInclusive, BigDecimal upperInclusive) {
        return fetchRange(Product.PRODUCT.PRICE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>price IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchByPrice(BigDecimal... values) {
        return fetch(Product.PRODUCT.PRICE, values);
    }

    /**
     * Fetch records that have <code>price_discount BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchRangeOfPriceDiscount(BigDecimal lowerInclusive, BigDecimal upperInclusive) {
        return fetchRange(Product.PRODUCT.PRICE_DISCOUNT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>price_discount IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Product> fetchByPriceDiscount(BigDecimal... values) {
        return fetch(Product.PRODUCT.PRICE_DISCOUNT, values);
    }
}
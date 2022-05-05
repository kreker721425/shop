/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.pojos;


import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Продукты
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long       id;
    private Long       article;
    private String     name;
    private Long       count;
    private String     description;
    private BigDecimal price;
    private BigDecimal priceDiscount;

    public Product() {}

    public Product(Product value) {
        this.id = value.id;
        this.article = value.article;
        this.name = value.name;
        this.count = value.count;
        this.description = value.description;
        this.price = value.price;
        this.priceDiscount = value.priceDiscount;
    }

    public Product(
        Long       id,
        Long       article,
        String     name,
        Long       count,
        String     description,
        BigDecimal price,
        BigDecimal priceDiscount
    ) {
        this.id = id;
        this.article = article;
        this.name = name;
        this.count = count;
        this.description = description;
        this.price = price;
        this.priceDiscount = priceDiscount;
    }

    /**
     * Getter for <code>shop.product.id</code>. Идентификатор продукта
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>shop.product.id</code>. Идентификатор продукта
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>shop.product.article</code>. Артикул продукта
     */
    public Long getArticle() {
        return this.article;
    }

    /**
     * Setter for <code>shop.product.article</code>. Артикул продукта
     */
    public void setArticle(Long article) {
        this.article = article;
    }

    /**
     * Getter for <code>shop.product.name</code>. Название продукта
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>shop.product.name</code>. Название продукта
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>shop.product.count</code>. Количество продукта
     */
    public Long getCount() {
        return this.count;
    }

    /**
     * Setter for <code>shop.product.count</code>. Количество продукта
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * Getter for <code>shop.product.description</code>. Описание продукта
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>shop.product.description</code>. Описание продукта
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for <code>shop.product.price</code>. Цена продукта
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Setter for <code>shop.product.price</code>. Цена продукта
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getter for <code>shop.product.price_discount</code>. Цена продукта со скидкой
     */
    public BigDecimal getPriceDiscount() {
        return this.priceDiscount;
    }

    /**
     * Setter for <code>shop.product.price_discount</code>. Цена продукта со скидкой
     */
    public void setPriceDiscount(BigDecimal priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product (");

        sb.append(id);
        sb.append(", ").append(article);
        sb.append(", ").append(name);
        sb.append(", ").append(count);
        sb.append(", ").append(description);
        sb.append(", ").append(price);
        sb.append(", ").append(priceDiscount);

        sb.append(")");
        return sb.toString();
    }
}

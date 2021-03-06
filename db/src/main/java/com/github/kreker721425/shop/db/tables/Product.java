/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables;


import com.github.kreker721425.shop.db.Keys;
import com.github.kreker721425.shop.db.Shop;
import com.github.kreker721425.shop.db.tables.records.ProductRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * Продукт
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Product extends TableImpl<ProductRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>shop.product</code>
     */
    public static final Product PRODUCT = new Product();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductRecord> getRecordType() {
        return ProductRecord.class;
    }

    /**
     * The column <code>shop.product.id</code>. Артикул
     */
    public final TableField<ProductRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "Артикул");

    /**
     * The column <code>shop.product.article</code>.
     */
    public final TableField<ProductRecord, String> ARTICLE = createField(DSL.name("article"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>shop.product.name</code>. Название
     */
    public final TableField<ProductRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "Название");

    /**
     * The column <code>shop.product.count</code>. Количество
     */
    public final TableField<ProductRecord, Integer> COUNT = createField(DSL.name("count"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("0", SQLDataType.INTEGER)), this, "Количество");

    /**
     * The column <code>shop.product.description</code>. Описание
     */
    public final TableField<ProductRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB, this, "Описание");

    /**
     * The column <code>shop.product.price</code>. Цена
     */
    public final TableField<ProductRecord, BigDecimal> PRICE = createField(DSL.name("price"), SQLDataType.NUMERIC.nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "Цена");

    /**
     * The column <code>shop.product.price_discount</code>. Цена продукта со скидкой
     */
    public final TableField<ProductRecord, BigDecimal> PRICE_DISCOUNT = createField(DSL.name("price_discount"), SQLDataType.NUMERIC.nullable(false).defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "Цена продукта со скидкой");

    private Product(Name alias, Table<ProductRecord> aliased) {
        this(alias, aliased, null);
    }

    private Product(Name alias, Table<ProductRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Продукт"), TableOptions.table());
    }

    /**
     * Create an aliased <code>shop.product</code> table reference
     */
    public Product(String alias) {
        this(DSL.name(alias), PRODUCT);
    }

    /**
     * Create an aliased <code>shop.product</code> table reference
     */
    public Product(Name alias) {
        this(alias, PRODUCT);
    }

    /**
     * Create a <code>shop.product</code> table reference
     */
    public Product() {
        this(DSL.name("product"), null);
    }

    public <O extends Record> Product(Table<O> child, ForeignKey<O, ProductRecord> key) {
        super(child, key, PRODUCT);
    }

    @Override
    public Schema getSchema() {
        return Shop.SHOP;
    }

    @Override
    public Identity<ProductRecord, Long> getIdentity() {
        return (Identity<ProductRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<ProductRecord> getPrimaryKey() {
        return Keys.PRODUCT_ID_PK;
    }

    @Override
    public List<UniqueKey<ProductRecord>> getKeys() {
        return Arrays.<UniqueKey<ProductRecord>>asList(Keys.PRODUCT_ID_PK);
    }

    @Override
    public Product as(String alias) {
        return new Product(DSL.name(alias), this);
    }

    @Override
    public Product as(Name alias) {
        return new Product(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Product rename(String name) {
        return new Product(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Product rename(Name name) {
        return new Product(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, String, Integer, String, BigDecimal, BigDecimal> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}

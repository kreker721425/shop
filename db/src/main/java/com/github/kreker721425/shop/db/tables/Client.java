/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables;


import com.github.kreker721425.shop.db.Keys;
import com.github.kreker721425.shop.db.Shop;
import com.github.kreker721425.shop.db.tables.records.ClientRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * Клиент
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Client extends TableImpl<ClientRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>shop.client</code>
     */
    public static final Client CLIENT = new Client();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClientRecord> getRecordType() {
        return ClientRecord.class;
    }

    /**
     * The column <code>shop.client.id</code>. Идентификатор
     */
    public final TableField<ClientRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "Идентификатор");

    /**
     * The column <code>shop.client.name</code>. Имя
     */
    public final TableField<ClientRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "Имя");

    /**
     * The column <code>shop.client.phone</code>. Номер телефона
     */
    public final TableField<ClientRecord, String> PHONE = createField(DSL.name("phone"), SQLDataType.CLOB.nullable(false), this, "Номер телефона");

    /**
     * The column <code>shop.client.bonus_count</code>. Количество бонусных баллов
     */
    public final TableField<ClientRecord, BigDecimal> BONUS_COUNT = createField(DSL.name("bonus_count"), SQLDataType.NUMERIC.defaultValue(DSL.field("0", SQLDataType.NUMERIC)), this, "Количество бонусных баллов");

    /**
     * The column <code>shop.client.birthday</code>. Дата рождения
     */
    public final TableField<ClientRecord, LocalDate> BIRTHDAY = createField(DSL.name("birthday"), SQLDataType.LOCALDATE, this, "Дата рождения");

    private Client(Name alias, Table<ClientRecord> aliased) {
        this(alias, aliased, null);
    }

    private Client(Name alias, Table<ClientRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Клиент"), TableOptions.table());
    }

    /**
     * Create an aliased <code>shop.client</code> table reference
     */
    public Client(String alias) {
        this(DSL.name(alias), CLIENT);
    }

    /**
     * Create an aliased <code>shop.client</code> table reference
     */
    public Client(Name alias) {
        this(alias, CLIENT);
    }

    /**
     * Create a <code>shop.client</code> table reference
     */
    public Client() {
        this(DSL.name("client"), null);
    }

    public <O extends Record> Client(Table<O> child, ForeignKey<O, ClientRecord> key) {
        super(child, key, CLIENT);
    }

    @Override
    public Schema getSchema() {
        return Shop.SHOP;
    }

    @Override
    public Identity<ClientRecord, Long> getIdentity() {
        return (Identity<ClientRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<ClientRecord> getPrimaryKey() {
        return Keys.CLIENT_ID_PK;
    }

    @Override
    public List<UniqueKey<ClientRecord>> getKeys() {
        return Arrays.<UniqueKey<ClientRecord>>asList(Keys.CLIENT_ID_PK);
    }

    @Override
    public Client as(String alias) {
        return new Client(DSL.name(alias), this);
    }

    @Override
    public Client as(Name alias) {
        return new Client(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Client rename(String name) {
        return new Client(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Client rename(Name name) {
        return new Client(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, String, BigDecimal, LocalDate> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}

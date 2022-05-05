/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db.tables.daos;


import com.github.kreker721425.shop.db.tables.Client;
import com.github.kreker721425.shop.db.tables.records.ClientRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * Клиенты
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ClientDao extends DAOImpl<ClientRecord, com.github.kreker721425.shop.db.tables.pojos.Client, Long> {

    /**
     * Create a new ClientDao without any configuration
     */
    public ClientDao() {
        super(Client.CLIENT, com.github.kreker721425.shop.db.tables.pojos.Client.class);
    }

    /**
     * Create a new ClientDao with an attached configuration
     */
    public ClientDao(Configuration configuration) {
        super(Client.CLIENT, com.github.kreker721425.shop.db.tables.pojos.Client.class, configuration);
    }

    @Override
    public Long getId(com.github.kreker721425.shop.db.tables.pojos.Client object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(Client.CLIENT.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchById(Long... values) {
        return fetch(Client.CLIENT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.github.kreker721425.shop.db.tables.pojos.Client fetchOneById(Long value) {
        return fetchOne(Client.CLIENT.ID, value);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(Client.CLIENT.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchByName(String... values) {
        return fetch(Client.CLIENT.NAME, values);
    }

    /**
     * Fetch records that have <code>phone BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchRangeOfPhone(String lowerInclusive, String upperInclusive) {
        return fetchRange(Client.CLIENT.PHONE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>phone IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchByPhone(String... values) {
        return fetch(Client.CLIENT.PHONE, values);
    }

    /**
     * Fetch records that have <code>bonus_count BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchRangeOfBonusCount(BigDecimal lowerInclusive, BigDecimal upperInclusive) {
        return fetchRange(Client.CLIENT.BONUS_COUNT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>bonus_count IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchByBonusCount(BigDecimal... values) {
        return fetch(Client.CLIENT.BONUS_COUNT, values);
    }

    /**
     * Fetch records that have <code>birthday BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchRangeOfBirthday(LocalDate lowerInclusive, LocalDate upperInclusive) {
        return fetchRange(Client.CLIENT.BIRTHDAY, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>birthday IN (values)</code>
     */
    public List<com.github.kreker721425.shop.db.tables.pojos.Client> fetchByBirthday(LocalDate... values) {
        return fetch(Client.CLIENT.BIRTHDAY, values);
    }
}
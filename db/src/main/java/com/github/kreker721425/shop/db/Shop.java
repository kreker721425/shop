/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db;


import com.github.kreker721425.shop.db.tables.Client;
import com.github.kreker721425.shop.db.tables.History;
import com.github.kreker721425.shop.db.tables.Order;
import com.github.kreker721425.shop.db.tables.Product;
import com.github.kreker721425.shop.db.tables.ProductOrder;
import com.github.kreker721425.shop.db.tables.Users;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Shop extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>shop</code>
     */
    public static final Shop SHOP = new Shop();

    /**
     * Клиент
     */
    public final Client CLIENT = Client.CLIENT;

    /**
     * История операций
     */
    public final History HISTORY = History.HISTORY;

    /**
     * Заказ
     */
    public final Order ORDER = Order.ORDER;

    /**
     * Продукт
     */
    public final Product PRODUCT = Product.PRODUCT;

    /**
     * Состав заказа
     */
    public final ProductOrder PRODUCT_ORDER = ProductOrder.PRODUCT_ORDER;

    /**
     * Пользователь
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Shop() {
        super("shop", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.CLIENT_ID_SEQ,
            Sequences.HISTORY_ID_SEQ,
            Sequences.ORDER_ID_SEQ,
            Sequences.PRODUCT_ID_SEQ,
            Sequences.PRODUCT_ORDER_ID_SEQ,
            Sequences.USERS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Client.CLIENT,
            History.HISTORY,
            Order.ORDER,
            Product.PRODUCT,
            ProductOrder.PRODUCT_ORDER,
            Users.USERS);
    }
}

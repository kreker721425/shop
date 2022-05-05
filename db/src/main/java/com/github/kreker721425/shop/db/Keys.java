/*
 * This file is generated by jOOQ.
 */
package com.github.kreker721425.shop.db;


import com.github.kreker721425.shop.db.tables.Client;
import com.github.kreker721425.shop.db.tables.Order;
import com.github.kreker721425.shop.db.tables.Product;
import com.github.kreker721425.shop.db.tables.ProductOrder;
import com.github.kreker721425.shop.db.tables.records.ClientRecord;
import com.github.kreker721425.shop.db.tables.records.OrderRecord;
import com.github.kreker721425.shop.db.tables.records.ProductOrderRecord;
import com.github.kreker721425.shop.db.tables.records.ProductRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * shop.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ClientRecord> CLIENT_ID_PK = Internal.createUniqueKey(Client.CLIENT, DSL.name("client_id_pk"), new TableField[] { Client.CLIENT.ID }, true);
    public static final UniqueKey<OrderRecord> ORDER_ID_PK = Internal.createUniqueKey(Order.ORDER, DSL.name("order_id_pk"), new TableField[] { Order.ORDER.ID }, true);
    public static final UniqueKey<ProductRecord> PRODUCT_ID_PK = Internal.createUniqueKey(Product.PRODUCT, DSL.name("product_id_pk"), new TableField[] { Product.PRODUCT.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<OrderRecord, ClientRecord> ORDER__CLIENT_ID__FK = Internal.createForeignKey(Order.ORDER, DSL.name("client_id__fk"), new TableField[] { Order.ORDER.CLIENT_ID }, Keys.CLIENT_ID_PK, new TableField[] { Client.CLIENT.ID }, true);
    public static final ForeignKey<ProductOrderRecord, OrderRecord> PRODUCT_ORDER__ORDER_ID__FK = Internal.createForeignKey(ProductOrder.PRODUCT_ORDER, DSL.name("order_id__fk"), new TableField[] { ProductOrder.PRODUCT_ORDER.ORDER_ID }, Keys.ORDER_ID_PK, new TableField[] { Order.ORDER.ID }, true);
    public static final ForeignKey<ProductOrderRecord, ProductRecord> PRODUCT_ORDER__PRODUCT_ID__FK = Internal.createForeignKey(ProductOrder.PRODUCT_ORDER, DSL.name("product_id__fk"), new TableField[] { ProductOrder.PRODUCT_ORDER.PRODUCT_ID }, Keys.PRODUCT_ID_PK, new TableField[] { Product.PRODUCT.ID }, true);
}

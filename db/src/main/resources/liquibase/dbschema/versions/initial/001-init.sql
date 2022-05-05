CREATE TABLE shop.client
(
    id bigserial NOT NULL,
    name text NOT NULL,
    phone text NOT NULL,
    bonus_count numeric NOT NULL DEFAULT 0,
    birthday date NOT NULL,
    CONSTRAINT client_id_pk PRIMARY KEY (id)
);

COMMENT ON TABLE shop.client IS 'Клиенты';
COMMENT ON COLUMN shop.client.id IS 'Идентификатор клиента';
COMMENT ON COLUMN shop.client.name IS 'Имя клиента';
COMMENT ON COLUMN shop.client.phone IS 'Номер телефона клиента';
COMMENT ON COLUMN shop.client.bonus_count IS 'Количество бонусных баллов клиента';
COMMENT ON COLUMN shop.client.birthday IS 'Дата рождения клиента';

CREATE TABLE shop.product
(
    id                  bigserial NOT NULL,
    article             bigint NOT NULL,
    name                text NOT NULL,
    count               bigint NOT NULL DEFAULT 0,
    description         text,
    price               numeric NOT NULL DEFAULT 0,
    price_discount      numeric NOT NULL DEFAULT 0,
    CONSTRAINT product_id_pk PRIMARY KEY (id)
);

COMMENT ON TABLE shop.product IS 'Продукты';
COMMENT ON COLUMN shop.product.id IS 'Идентификатор продукта';
COMMENT ON COLUMN shop.product.article IS 'Артикул продукта';
COMMENT ON COLUMN shop.product.name IS 'Название продукта';
COMMENT ON COLUMN shop.product.count IS 'Количество продукта';
COMMENT ON COLUMN shop.product.description IS 'Описание продукта';
COMMENT ON COLUMN shop.product.price IS 'Цена продукта';
COMMENT ON COLUMN shop.product.price_discount IS 'Цена продукта со скидкой';

CREATE TABLE shop.order
(
    id                  bigserial NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    price               numeric NOT NULL DEFAULT 0,
    client_id           bigint NOT NULL,
    bonus_count         numeric NOT NULL DEFAULT 0,
    CONSTRAINT order_id_pk PRIMARY KEY (id),
    CONSTRAINT client_id__fk FOREIGN KEY (client_id)
            REFERENCES shop.client (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE NO ACTION
);

COMMENT ON TABLE shop.order IS 'Заказ';
COMMENT ON COLUMN shop.order.id IS 'Идентификатор заказа';
COMMENT ON COLUMN shop.order.created_at IS 'Время создания заказа';
COMMENT ON COLUMN shop.order.price IS 'Стоимость заказа';
COMMENT ON COLUMN shop.order.client_id IS 'Идентификатор клиента заказа';
COMMENT ON COLUMN shop.order.bonus_count IS 'Количество бонусных баллов за заказ';

CREATE TABLE shop.product_order
(
    product_id      bigint NOT NULL,
    order_id        bigint NOT NULL,
    count           bigint NOT NULL,
    CONSTRAINT product_id__fk FOREIGN KEY (product_id)
            REFERENCES shop.product (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT order_id__fk FOREIGN KEY (order_id)
        REFERENCES shop.order (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

COMMENT ON TABLE shop.product_order IS 'Состав заказа';
COMMENT ON COLUMN shop.product_order.product_id IS 'Идентификатор продукта';
COMMENT ON COLUMN shop.product_order.order_id IS 'Идентификатор заказа';
COMMENT ON COLUMN shop.product_order.count IS 'Количество продукта в заказе';
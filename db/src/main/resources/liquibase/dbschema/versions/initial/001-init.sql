CREATE TABLE shop.client
(
    id bigserial NOT NULL,
    name text NOT NULL,
    phone text NOT NULL,
    bonus_count numeric DEFAULT 0,
    birthday date,
    CONSTRAINT client_id_pk PRIMARY KEY (id)
);

COMMENT ON TABLE shop.client IS 'Клиент';
COMMENT ON COLUMN shop.client.id IS 'Идентификатор';
COMMENT ON COLUMN shop.client.name IS 'Имя';
COMMENT ON COLUMN shop.client.phone IS 'Номер телефона';
COMMENT ON COLUMN shop.client.bonus_count IS 'Количество бонусных баллов';
COMMENT ON COLUMN shop.client.birthday IS 'Дата рождения';

CREATE TABLE shop.product
(
    id                  bigserial NOT NULL,
    name                text NOT NULL,
    count               int NOT NULL DEFAULT 0,
    description         text,
    price               numeric NOT NULL DEFAULT 0,
    price_discount      numeric NOT NULL DEFAULT 0,
    CONSTRAINT product_id_pk PRIMARY KEY (id)
);

COMMENT ON TABLE shop.product IS 'Продукт';
COMMENT ON COLUMN shop.product.id IS 'Идентификатор';
COMMENT ON COLUMN shop.product.name IS 'Название';
COMMENT ON COLUMN shop.product.count IS 'Количество';
COMMENT ON COLUMN shop.product.description IS 'Описание';
COMMENT ON COLUMN shop.product.price IS 'Цена';
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
COMMENT ON COLUMN shop.order.id IS 'Идентификатор';
COMMENT ON COLUMN shop.order.created_at IS 'Время создания';
COMMENT ON COLUMN shop.order.price IS 'Стоимость';
COMMENT ON COLUMN shop.order.client_id IS 'Идентификатор клиента';
COMMENT ON COLUMN shop.order.bonus_count IS 'Количество бонусных баллов за заказ';

CREATE TABLE shop.product_order
(
    id              bigserial NOT NULL,
    product_id      bigint NOT NULL,
    order_id        bigint NOT NULL,
    count           bigint NOT NULL,
    CONSTRAINT product_order_id_pk PRIMARY KEY (id),
    CONSTRAINT product_id__fk FOREIGN KEY (product_id)
            REFERENCES shop.product (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT order_id__fk FOREIGN KEY (order_id)
            REFERENCES shop.order (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE NO ACTION
);

COMMENT ON TABLE shop.product_order IS 'Состав заказа';
COMMENT ON COLUMN shop.order.id IS 'Идентификатор';
COMMENT ON COLUMN shop.product_order.product_id IS 'Идентификатор продукта';
COMMENT ON COLUMN shop.product_order.order_id IS 'Идентификатор заказа';
COMMENT ON COLUMN shop.product_order.count IS 'Количество продукта в заказе';

CREATE TYPE shop.user_role_enum AS ENUM(
            'ADMIN',
            'USER'
);

CREATE TYPE shop.user_status_enum AS ENUM(
            'ACTIVE',
            'DISABLED'
);

CREATE TABLE shop.users
(
    id              bigserial NOT NULL,
    name            text NOT NULL,
    login           text NOT NULL,
    password        text NOT NULL,
    role            shop.user_role_enum DEFAULT 'USER'::shop.user_role_enum NOT NULL,
    status          shop.user_status_enum DEFAULT 'ACTIVE'::shop.user_status_enum NOT NULL,
    CONSTRAINT users_id_pk PRIMARY KEY (id)
);

COMMENT ON TABLE shop.users IS 'Пользователь';
COMMENT ON COLUMN shop.users.id IS 'Идентификатор';
COMMENT ON COLUMN shop.users.name IS 'Имя';
COMMENT ON COLUMN shop.users.login IS 'Логин';
COMMENT ON COLUMN shop.users.password IS 'Пароль';
COMMENT ON COLUMN shop.users.role IS 'Роль';
COMMENT ON COLUMN shop.users.status IS 'Статус';

CREATE TYPE shop.table_enum AS ENUM(
            'CLIENT',
            'ORDER',
            'USER',
            'PRODUCT'
);

CREATE TYPE shop.operation_enum AS ENUM(
            'ADD',
            'PUT',
            'DELETE'
);

CREATE TABLE shop.history
(
    id                  bigserial NOT NULL,
    table_name          shop.table_enum NOT NULL,
    operation           shop.operation_enum NOT NULL,
    old_value           text NOT NULL,
    new_value           text NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    user_id             bigint NOT NULL,
    CONSTRAINT history_id_pk PRIMARY KEY (id),
    CONSTRAINT user_id__fk FOREIGN KEY (user_id)
                REFERENCES shop.users (id) MATCH SIMPLE
                ON UPDATE NO ACTION ON DELETE NO ACTION
);

COMMENT ON TABLE shop.history IS 'История операций';
COMMENT ON COLUMN shop.history.id IS 'Идентификатор';
COMMENT ON COLUMN shop.history.table_name IS 'Название таблицы';
COMMENT ON COLUMN shop.history.operation IS 'Операция';
COMMENT ON COLUMN shop.history.old_value IS 'Старое значение';
COMMENT ON COLUMN shop.history.new_value IS 'Новое значение';
COMMENT ON COLUMN shop.history.created_at IS 'Время создания';
COMMENT ON COLUMN shop.history.user_id IS 'Идентификатор пользователя';
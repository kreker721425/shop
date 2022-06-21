package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.OrderDao;
import com.github.kreker721425.shop.db.tables.pojos.Order;
import com.github.kreker721425.shop.repository.filter.OrderFilter;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kreker721425.shop.db.Tables.CLIENT;
import static com.github.kreker721425.shop.db.Tables.ORDER;
import static org.jooq.impl.DSL.using;

@Repository
public class OrderRepository extends OrderDao {

    public OrderRepository(Configuration configuration) {
        super(configuration);
    }

    public List<Order> search(OrderFilter filter) {
        if (Objects.isNull(filter)) {
            return findAll();
        }
        var query = buildQuery(filter);
        query.addSelect(ORDER.fields());
        query.addOrderBy(ORDER.ID.asc());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Order.class);
    }

    private SelectQuery<Record> buildQuery(OrderFilter filter) {
        var query = using(configuration()).selectQuery();
        if (StringUtils.isNotBlank(filter.getClientPhone())) {
            query.addFrom(ORDER
                    .join(CLIENT)
                    .on(ORDER.CLIENT_ID.eq(CLIENT.ID)));
        } else {
            query.addFrom(ORDER);
        }

        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        return query;
    }

    private List<Condition> getConditions(OrderFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (Objects.nonNull(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(ORDER.ID, filter.getId()));
        }
        if (StringUtils.isNotBlank(filter.getClientPhone())) {
            conditions.add(PaginationUtils.getOrLikeCondition(CLIENT.PHONE, filter.getClientPhone()));
        }
        if (Objects.nonNull(filter.getCreatedAtStart())) {
            conditions.add(ORDER.CREATED_AT.greaterOrEqual(filter.getCreatedAtStart()));
        }
        if (Objects.nonNull(filter.getCreatedAtStart())) {
            conditions.add(ORDER.CREATED_AT.lessOrEqual(filter.getCreatedAtEnd()));
        }
        return conditions;
    }
}

package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.OrderDao;
import com.github.kreker721425.shop.db.tables.pojos.Order;
import com.github.kreker721425.shop.repository.filter.OrderFilter;
import com.github.kreker721425.shop.repository.sort.OrderSort;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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

    public List<Order> search(OrderFilter filter, OrderSort sort) {
        if (Objects.isNull(filter) && Objects.isNull(sort)) {
            return findAll();
        }
        var query = buildQuery(filter, sort);
        query.addSelect(ORDER.fields());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Order.class);
    }

    public Integer searchCount(OrderFilter filter) {
        var query = buildQuery(filter, null);
        query.addSelect(DSL.count());
        return query.fetchOneInto(Integer.class);
    }

    private SelectQuery<Record> buildQuery(OrderFilter filter, OrderSort sort) {
        var query = using(configuration()).selectQuery();
        query.addFrom(ORDER
                .join(CLIENT)
                .on(ORDER.CLIENT_ID.eq(CLIENT.ID)));
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        if (Objects.nonNull(sort)) {
            query.addOrderBy(getOrderFields(sort));
        } else {
            query.addOrderBy(ORDER.ID.asc());
        }
        return query;
    }

    private List<Condition> getConditions(OrderFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(ORDER.ID, filter.getId()));
        }
        if (!CollectionUtils.isEmpty(filter.getClientName())) {
            conditions.add(PaginationUtils.getOrLikeCondition(CLIENT.NAME, filter.getClientName()));
        }
        if (Objects.nonNull(filter.getCreatedAtStart())) {
            conditions.add(ORDER.CREATED_AT.greaterOrEqual(filter.getCreatedAtStart().atTime(LocalTime.MIN)));
        }
        if (Objects.nonNull(filter.getCreatedAtStart())) {
            conditions.add(ORDER.CREATED_AT.lessOrEqual(filter.getCreatedAtEnd().atTime(LocalTime.MAX)));
        }
        return conditions;
    }

    private SortField[] getOrderFields(OrderSort sort) {
        List<SortField> sortFields = new ArrayList<>();
        if (Objects.nonNull(sort.getId())) {
            sortFields.add(sort.getId() ? ORDER.ID.asc() : ORDER.ID.desc());
        }
        if (Objects.nonNull(sort.getClientName())) {
            sortFields.add(sort.getClientName() ? CLIENT.NAME.asc() : CLIENT.NAME.desc());
        }
        if (Objects.nonNull(sort.getCreatedAt())) {
            sortFields.add(sort.getCreatedAt() ? ORDER.CREATED_AT.asc() : ORDER.CREATED_AT.desc());
        }
        if (Objects.nonNull(sort.getPrice())) {
            sortFields.add(sort.getPrice() ? ORDER.PRICE.asc() : ORDER.PRICE.desc());
        }
        if (Objects.nonNull(sort.getBonusCount())) {
            sortFields.add(sort.getBonusCount() ? ORDER.BONUS_COUNT.asc() : ORDER.BONUS_COUNT.desc());
        }
        return sortFields.toArray(SortField[]::new);
    }
}

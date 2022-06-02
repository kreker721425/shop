package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.ProductOrderDao;
import com.github.kreker721425.shop.db.tables.pojos.ProductOrder;
import com.github.kreker721425.shop.repository.filter.ProductOrderFilter;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kreker721425.shop.db.Tables.ORDER;
import static com.github.kreker721425.shop.db.Tables.PRODUCT_ORDER;
import static org.jooq.impl.DSL.using;

@Repository
public class ProductOrderRepository extends ProductOrderDao {

    public ProductOrderRepository(Configuration configuration) {
        super(configuration);
    }

    public List<ProductOrder> search(ProductOrderFilter filter) {
        if (Objects.isNull(filter)) {
            return findAll();
        }
        var query = buildQuery(filter);
        query.addSelect(PRODUCT_ORDER.fields());
        query.addOrderBy(ORDER.ID.asc());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(ProductOrder.class);
    }

    private SelectQuery<Record> buildQuery(ProductOrderFilter filter) {
        var query = using(configuration()).selectQuery();
        query.addFrom(PRODUCT_ORDER);
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        return query;
    }

    private List<Condition> getConditions(ProductOrderFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (Objects.nonNull(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT_ORDER.ID, filter.getId()));
        }
        if (Objects.nonNull(filter.getOrderId())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT_ORDER.ORDER_ID, filter.getOrderId()));
        }
        if (Objects.nonNull(filter.getProductId())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT_ORDER.PRODUCT_ID, filter.getProductId()));
        }
        if (Objects.nonNull(filter.getCount())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT_ORDER.COUNT, filter.getCount()));
        }
        return conditions;
    }
}
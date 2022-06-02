package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.ProductDao;
import com.github.kreker721425.shop.db.tables.pojos.Product;
import com.github.kreker721425.shop.repository.filter.ProductFilter;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kreker721425.shop.db.Tables.PRODUCT;
import static org.jooq.impl.DSL.using;

@Repository
public class ProductRepository extends ProductDao {

    public ProductRepository(Configuration configuration) {
        super(configuration);
    }

    public List<Product> search(ProductFilter filter) {
        if (Objects.isNull(filter)) {
            return findAll();
        }
        var query = buildQuery(filter);
        query.addSelect(PRODUCT.fields());
        query.addOrderBy(PRODUCT.ID.asc());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Product.class);
    }

    private SelectQuery<Record> buildQuery(ProductFilter filter) {
        var query = using(configuration()).selectQuery();
        query.addFrom(PRODUCT);
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        return query;
    }

    private List<Condition> getConditions(ProductFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (Objects.nonNull(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.ID, filter.getId()));
        }
        if (Objects.nonNull(filter.getName())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT.NAME, filter.getName()));
        }
        if (Objects.nonNull(filter.getCount())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.COUNT, filter.getCount()));
        }
        if (Objects.nonNull(filter.getDescription())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT.DESCRIPTION, filter.getDescription()));
        }
        if (Objects.nonNull(filter.getPrice())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.PRICE, filter.getPrice()));
        }
        if (Objects.nonNull(filter.getPriceDiscount())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.PRICE_DISCOUNT, filter.getPriceDiscount()));
        }
        return conditions;
    }
}
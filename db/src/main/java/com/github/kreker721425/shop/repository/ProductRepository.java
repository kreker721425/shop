package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.ProductDao;
import com.github.kreker721425.shop.db.tables.pojos.Product;
import com.github.kreker721425.shop.repository.filter.ProductFilter;
import com.github.kreker721425.shop.repository.sort.ProductSort;
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
import static com.github.kreker721425.shop.db.Tables.PRODUCT;
import static org.jooq.impl.DSL.using;

@Repository
public class ProductRepository extends ProductDao {

    public ProductRepository(Configuration configuration) {
        super(configuration);
    }

    public List<Product> search(ProductFilter filter, ProductSort sort) {
        if (Objects.isNull(filter) && Objects.isNull(sort)) {
            return findAll();
        }
        var query = buildQuery(filter, sort);
        query.addSelect(PRODUCT.fields());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Product.class);
    }

    public Integer searchCount(ProductFilter filter) {
        var query = buildQuery(filter, null);
        query.addSelect(DSL.count());
        return query.fetchOneInto(Integer.class);
    }

    private SelectQuery<Record> buildQuery(ProductFilter filter, ProductSort sort) {
        var query = using(configuration()).selectQuery();
        query.addFrom(PRODUCT);
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        if (Objects.nonNull(sort)) {
            query.addOrderBy(getOrderFields(sort));
        } else {
            query.addOrderBy(PRODUCT.ID.asc());
        }
        return query;
    }

    private List<Condition> getConditions(ProductFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.ID, filter.getId()));
        }
        if (!CollectionUtils.isEmpty(filter.getArticle())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.ARTICLE, filter.getArticle()));
        }
        if (!CollectionUtils.isEmpty(filter.getName())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT.NAME, filter.getName()));
        }
        if (!CollectionUtils.isEmpty(filter.getCount())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.COUNT, filter.getCount()));
        }
        if (!CollectionUtils.isEmpty(filter.getDescription())) {
            conditions.add(PaginationUtils.getOrLikeCondition(PRODUCT.DESCRIPTION, filter.getDescription()));
        }
        if (!CollectionUtils.isEmpty(filter.getPrice())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.PRICE, filter.getPrice()));
        }
        if (!CollectionUtils.isEmpty(filter.getPriceDiscount())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(PRODUCT.PRICE_DISCOUNT, filter.getPriceDiscount()));
        }
        return conditions;
    }

    private SortField[] getOrderFields(ProductSort sort) {
        List<SortField> sortFields = new ArrayList<>();
        if (Objects.nonNull(sort.getId())) {
            sortFields.add(sort.getId() ? PRODUCT.ID.asc() : PRODUCT.ID.desc());
        }
        if (Objects.nonNull(sort.getArticle())) {
            sortFields.add(sort.getArticle() ? PRODUCT.ARTICLE.asc() : PRODUCT.ARTICLE.desc());
        }
        if (Objects.nonNull(sort.getName())) {
            sortFields.add(sort.getName() ? PRODUCT.NAME.asc() : PRODUCT.NAME.desc());
        }
        if (Objects.nonNull(sort.getCount())) {
            sortFields.add(sort.getCount() ? PRODUCT.COUNT.asc() : PRODUCT.COUNT.desc());
        }
        if (Objects.nonNull(sort.getDescription())) {
            sortFields.add(sort.getDescription() ? PRODUCT.DESCRIPTION.asc() : PRODUCT.DESCRIPTION.desc());
        }
        if (Objects.nonNull(sort.getPrice())) {
            sortFields.add(sort.getPrice() ? PRODUCT.PRICE.asc() : PRODUCT.PRICE.desc());
        }
        if (Objects.nonNull(sort.getPriceDiscount())) {
            sortFields.add(sort.getPriceDiscount() ? PRODUCT.PRICE_DISCOUNT.asc() : PRODUCT.PRICE_DISCOUNT.desc());
        }
        return sortFields.toArray(SortField[]::new);
    }
}
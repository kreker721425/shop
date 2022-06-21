package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.db.tables.daos.HistoryDao;
import com.github.kreker721425.shop.db.tables.pojos.Client;
import com.github.kreker721425.shop.db.tables.pojos.History;
import com.github.kreker721425.shop.repository.filter.ClientFilter;
import com.github.kreker721425.shop.repository.filter.HistoryFilter;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kreker721425.shop.db.Tables.*;
import static org.jooq.impl.DSL.using;

@Repository
public class HistoryRepository extends HistoryDao {

    public HistoryRepository(Configuration configuration) {
        super(configuration);
    }

    public List<History> search(HistoryFilter filter) {
        if (Objects.isNull(filter)) {
            return findAll();
        }
        var query = buildQuery(filter);
        query.addSelect(HISTORY.fields());
        query.addOrderBy(HISTORY.ID.asc());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(History.class);
    }

    private SelectQuery<Record> buildQuery(HistoryFilter filter) {
        var query = using(configuration()).selectQuery();
        query.addFrom(HISTORY
                .join(USERS).on(HISTORY.USER_ID.eq(USERS.ID)));
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        return query;
    }

    private List<Condition> getConditions(HistoryFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (Objects.nonNull(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(HISTORY.ID, filter.getId()));
        }
        if (Objects.nonNull(filter.getTableName())) {
            conditions.add(HISTORY.TABLE_NAME.eq(filter.getTableName()));
        }
        if (Objects.nonNull(filter.getOperation())) {
            conditions.add(HISTORY.OPERATION.eq(filter.getOperation()));
        }
        if (Objects.nonNull(filter.getCreatedAtStart())) {
            conditions.add(HISTORY.CREATED_AT.greaterOrEqual(filter.getCreatedAtStart()));
        }
        if (Objects.nonNull(filter.getCreatedAtEnd())) {
            conditions.add(HISTORY.CREATED_AT.lessOrEqual(filter.getCreatedAtEnd()));
        }
        if (Objects.nonNull(filter.getUser())) {
            conditions.add(PaginationUtils.getOrLikeCondition(USERS.NAME, filter.getUser()));
        }
        return conditions;
    }
}

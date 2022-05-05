package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.ClientDao;
import com.github.kreker721425.shop.db.tables.pojos.Client;
import com.github.kreker721425.shop.repository.filter.ClientFilter;
import com.github.kreker721425.shop.repository.sort.ClientSort;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kreker721425.shop.db.Tables.CLIENT;
import static org.jooq.impl.DSL.using;

@Repository
public class ClientRepository extends ClientDao {

    public ClientRepository(Configuration configuration) {
        super(configuration);
    }

    public List<Client> search(ClientFilter filter, ClientSort sort) {
        if (Objects.isNull(filter) && Objects.isNull(sort)) {
            return findAll();
        }
        var query = buildQuery(filter, sort);
        query.addSelect(CLIENT.fields());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Client.class);
    }

    public Integer searchCount(ClientFilter filter) {
        var query = buildQuery(filter, null);
        query.addSelect(DSL.count());
        return query.fetchOneInto(Integer.class);
    }

    private SelectQuery<Record> buildQuery(ClientFilter filter, ClientSort sort) {
        var query = using(configuration()).selectQuery();
        query.addFrom(CLIENT);
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        if (Objects.nonNull(sort)) {
            query.addOrderBy(getOrderFields(sort));
        } else {
            query.addOrderBy(CLIENT.ID.asc());
        }
        return query;
    }

    private List<Condition> getConditions(ClientFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(CLIENT.ID, filter.getId()));
        }
        if (!CollectionUtils.isEmpty(filter.getName())) {
            conditions.add(PaginationUtils.getOrLikeCondition(CLIENT.NAME, filter.getName()));
        }
        if (Objects.nonNull(filter.getBirthday())) {
            conditions.add(CLIENT.BIRTHDAY.eq(filter.getBirthday()));
        }
        if (!CollectionUtils.isEmpty(filter.getPhone())) {
            conditions.add(PaginationUtils.getOrLikeCondition(CLIENT.PHONE, filter.getPhone()));
        }
        return conditions;
    }

    private SortField[] getOrderFields(ClientSort sort) {
        List<SortField> sortFields = new ArrayList<>();
        if (Objects.nonNull(sort.getId())) {
            sortFields.add(sort.getId() ? CLIENT.ID.asc() : CLIENT.ID.desc());
        }
        if (Objects.nonNull(sort.getName())) {
            sortFields.add(sort.getName() ? CLIENT.NAME.asc() : CLIENT.NAME.desc());
        }
        if (Objects.nonNull(sort.getPhone())) {
            sortFields.add(sort.getPhone() ? CLIENT.PHONE.asc() : CLIENT.PHONE.desc());
        }
        if (Objects.nonNull(sort.getBirthday())) {
            sortFields.add(sort.getBirthday() ? CLIENT.BIRTHDAY.asc() : CLIENT.BIRTHDAY.desc());
        }
        if (Objects.nonNull(sort.getBonusCount())) {
            sortFields.add(sort.getBonusCount() ? CLIENT.BONUS_COUNT.asc() : CLIENT.BONUS_COUNT.desc());
        }
        return sortFields.toArray(SortField[]::new);
    }
}

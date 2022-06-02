package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.ClientDao;
import com.github.kreker721425.shop.db.tables.pojos.Client;
import com.github.kreker721425.shop.repository.filter.ClientFilter;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.springframework.stereotype.Repository;

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

    public List<Client> search(ClientFilter filter) {
        if (Objects.isNull(filter)) {
            return findAll();
        }
        var query = buildQuery(filter);
        query.addOrderBy(CLIENT.ID.asc());
        query.addSelect(CLIENT.fields());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Client.class);
    }

    private SelectQuery<Record> buildQuery(ClientFilter filter) {
        var query = using(configuration()).selectQuery();
        query.addFrom(CLIENT);
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        return query;
    }

    private List<Condition> getConditions(ClientFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (Objects.nonNull(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(CLIENT.ID, filter.getId()));
        }
        if (Objects.nonNull(filter.getName())) {
            conditions.add(PaginationUtils.getOrLikeCondition(CLIENT.NAME, filter.getName()));
        }
        if (Objects.nonNull(filter.getBirthday())) {
            conditions.add(CLIENT.BIRTHDAY.eq(filter.getBirthday()));
        }
        if (Objects.nonNull(filter.getPhone())) {
            conditions.add(PaginationUtils.getOrLikeCondition(CLIENT.PHONE, filter.getPhone()));
        }
        return conditions;
    }
}

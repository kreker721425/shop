package com.github.kreker721425.shop.repository;

import com.github.kreker721425.shop.db.tables.daos.UsersDao;
import com.github.kreker721425.shop.db.tables.pojos.Users;
import com.github.kreker721425.shop.repository.filter.UserFilter;
import com.github.kreker721425.shop.utils.PaginationUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.kreker721425.shop.db.Tables.USERS;
import static org.jooq.impl.DSL.using;

@Repository
public class UserRepository extends UsersDao {

    public UserRepository(Configuration configuration) {
        super(configuration);
    }

    public List<Users> search(UserFilter filter) {
        if (Objects.isNull(filter)) {
            return findAll();
        }
        var query = buildQuery(filter);
        query.addOrderBy(USERS.ID.asc());
        query.addSelect(USERS.fields());

        if (filter.getLimit() > 0) {
            query.addLimit(filter.getLimit());
            query.addOffset(PaginationUtils.calcOffset(filter.getPage(), filter.getLimit()));
        }

        return query.fetchInto(Users.class);
    }

    private SelectQuery<Record> buildQuery(UserFilter filter) {
        var query = using(configuration()).selectQuery();
        query.addFrom(USERS);
        var conditionsList = getConditions(filter);
        if (!conditionsList.isEmpty()) {
            query.addConditions(conditionsList);
        }
        return query;
    }

    private List<Condition> getConditions(UserFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        if (Objects.nonNull(filter.getId())) {
            conditions.add(PaginationUtils.getOrLikeConditionForNumeric(USERS.ID, filter.getId()));
        }
        if (Objects.nonNull(filter.getName())) {
            conditions.add(PaginationUtils.getOrLikeCondition(USERS.NAME, filter.getName()));
        }
        if (Objects.nonNull(filter.getLogin())) {
            conditions.add(USERS.LOGIN.eq(filter.getLogin()));
        }
        if (Objects.nonNull(filter.getRole())) {
            conditions.add(USERS.ROLE.eq(filter.getRole()));
        }
        if (Objects.nonNull(filter.getStatus())) {
            conditions.add(USERS.STATUS.eq(filter.getStatus()));
        }
        return conditions;
    }
}

package com.github.kreker721425.shop.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.TableField;
import org.jooq.impl.DSL;

import java.util.List;

import static com.github.kreker721425.shop.db.Tables.CLIENT;
import static org.jooq.impl.DSL.cast;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static Integer calcTotalPages(Integer totalElements, Integer limit) {
        if (limit != null && limit > 0 && totalElements != null) {
            return (int) Math.ceil(totalElements.doubleValue() / limit.doubleValue());
        }
        return null;
    }

    public static Integer calcOffset(Integer page, Integer limit) {
        if (limit != null && page != null) {
            return (page - 1) * limit;
        }
        return null;
    }

    public static Condition getOrLikeCondition(TableField field, List<String> array) {
        Condition cond = DSL.noCondition();
        for (String f : array) {
            cond = cond.or(field.likeIgnoreCase(f));
        }
        return cond;
    }

    public static Condition getOrLikeConditionForNumeric(TableField field, List<String> array) {
        Condition cond = DSL.noCondition();
        for (String id : array) {
            cond = cond.or(cast(field, String.class).likeIgnoreCase(id));
        }
        return cond;
    }
}

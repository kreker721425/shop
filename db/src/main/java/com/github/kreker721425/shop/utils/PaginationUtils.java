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

    public static Integer calcOffset(Integer page, Integer limit) {
        if (limit != null && page != null) {
            return (page - 1) * limit;
        }
        return null;
    }

    public static Condition getOrLikeCondition(TableField field, String filter) {
        return DSL.noCondition().or(field.likeIgnoreCase("%".concat(filter).concat("%")));
    }

    public static Condition getOrLikeConditionForNumeric(TableField field, String id) {
        return DSL.noCondition()
                .or(cast(field, String.class).likeIgnoreCase("%".concat(id).concat("%")));
    }
}

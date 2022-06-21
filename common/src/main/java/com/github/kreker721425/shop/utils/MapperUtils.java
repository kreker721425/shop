package com.github.kreker721425.shop.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtils {

    public static String getStringFilterFromString(String stringFilter) {
        if (StringUtils.isNotBlank(stringFilter)) {
            stringFilter = StringUtils.replace(stringFilter, "%", "\\%");
            stringFilter = StringUtils.replace(stringFilter, "_", "\\_");
            return "%".concat(stringFilter.strip()).concat("%");
        }
        return null;
    }

    public static Long getLongFilterFromString(String stringFilter) {
        if (StringUtils.isNotBlank(stringFilter)) {
            try {
                return Long.valueOf(stringFilter.strip());
            } catch (NumberFormatException ignore) {
            }
        }
        return null;
    }


}

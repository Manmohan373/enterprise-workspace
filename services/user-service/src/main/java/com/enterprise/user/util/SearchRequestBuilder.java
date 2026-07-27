package com.enterprise.user.util;

import com.enterprise.user.dto.common.SearchRequest;

import java.util.HashMap;
import java.util.Map;

public final class SearchRequestBuilder {

    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private static final String SORT_BY = "sortBy";
    private static final String SORT_DIRECTION = "sortDirection";

    private SearchRequestBuilder() {
    }

    public static SearchRequest from(Map<String, String> params) {

        Map<String, Object> filters = new HashMap<>();

        params.forEach((key, value) -> {

            if (!isReservedKey(key)) {
                filters.put(key, value);
            }

        });

        return SearchRequest.builder()
            .page(parseInteger(params.get(PAGE), 0))
            .size(parseInteger(params.get(SIZE), 10))
            .sortBy(params.getOrDefault(SORT_BY, "createdAt"))
            .sortDirection(params.getOrDefault(SORT_DIRECTION, "DESC"))
            .filters(filters)
            .build();
    }

    private static boolean isReservedKey(String key) {

        return PAGE.equals(key)
            || SIZE.equals(key)
            || SORT_BY.equals(key)
            || SORT_DIRECTION.equals(key);
    }

    private static Integer parseInteger(String value, Integer defaultValue) {

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }

    }

}

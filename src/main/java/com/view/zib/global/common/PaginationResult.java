package com.view.zib.global.common;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public record PaginationResult<T extends Pagination>(
        T content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
    public static <T extends Pagination> PaginationResult<T> from(T content) {
        return new PaginationResult<>(content, 0, 0, 0, 0, false, false);
//        return new PaginationResult<>(content);
    }

    public record Sort(
            boolean sorted,
            List<Field> fields
    ) {

        public record Field(
                String field,
                Direction direction
        ) {
        }


        enum Direction {
            ASC, DESC
        }
    }
}
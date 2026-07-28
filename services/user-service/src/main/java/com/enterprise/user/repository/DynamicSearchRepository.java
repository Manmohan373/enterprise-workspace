package com.enterprise.user.repository;

import com.enterprise.user.constants.SearchConstants;
import com.enterprise.user.dto.common.SearchRequest;
import com.enterprise.user.dto.common.SearchResponse;
import com.enterprise.user.exception.InvalidSearchFieldException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class DynamicSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public <T> SearchResponse<T> search(
        Class<T> entityClass,
        SearchRequest request
    ) {

        log.debug(
            "Searching {} with filters {}",
            entityClass.getSimpleName(),
            request.filters()
        );

        Pageable pageable = buildPageable(request);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> query = cb.createQuery(entityClass);

        Root<T> root = query.from(entityClass);

        List<Predicate> predicates = buildPredicates(
            cb,
            root,
            request.filters()
        );

        query.where(predicates.toArray(new Predicate[0]));

        applySorting(
            query,
            root,
            cb,
            pageable
        );

        TypedQuery<T> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult((int) pageable.getOffset());

        typedQuery.setMaxResults(pageable.getPageSize());

        List<T> data = typedQuery.getResultList();

        long total = getTotalCount(
            entityClass,
            request.filters()
        );

        return buildResponse(
            data,
            pageable,
            total
        );
    }

    // =====================================================
    // Pageable
    // =====================================================

    private Pageable buildPageable(SearchRequest request) {

        Sort.Direction direction;

        try {
            direction = Sort.Direction.fromString(request.sortDirection());
        } catch (IllegalArgumentException ex) {
            direction = Sort.Direction.ASC;
        }

        return PageRequest.of(
            request.page(),
            Math.min(request.size(), 100),
            Sort.by(direction, request.sortBy())
        );
    }

    // =====================================================
    // Sorting
    // =====================================================

    private <T> void applySorting(
        CriteriaQuery<T> query,
        Root<T> root,
        CriteriaBuilder cb,
        Pageable pageable
    ) {

        if (!pageable.getSort().isSorted()) {
            return;
        }

        List<Order> orders = new ArrayList<>();

        pageable.getSort().forEach(sort -> {

            validateField(root, sort.getProperty());

            Order order = sort.isAscending()
                ? cb.asc(root.get(sort.getProperty()))
                : cb.desc(root.get(sort.getProperty()));

            orders.add(order);

        });

        query.orderBy(orders);
    }


    private <T> long getTotalCount(
        Class<T> entityClass,
        Map<String, Object> filters
    ) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> query =
            cb.createQuery(Long.class);

        Root<T> root =
            query.from(entityClass);

        query.select(cb.count(root));

        query.where(
            buildPredicates(
                cb,
                root,
                filters
            ).toArray(new Predicate[0])
        );

        return entityManager
            .createQuery(query)
            .getSingleResult();
    }

    private <T> SearchResponse<T> buildResponse(
        List<T> data,
        Pageable pageable,
        long total
    ) {

        int totalPages =
            (int) Math.ceil(
                (double) total / pageable.getPageSize()
            );

        return SearchResponse.<T>builder()
            .data(data)
            .page(pageable.getPageNumber())
            .size(pageable.getPageSize())
            .totalElements(total)
            .totalPages(totalPages)
            .first(pageable.getPageNumber() == 0)
            .last(pageable.getPageNumber() >= totalPages - 1)
            .build();
    }

    // =====================================================
    // Dynamic Filters
    // =====================================================

    private <T> List<Predicate> buildPredicates(
        CriteriaBuilder cb,
        Root<T> root,
        Map<String, Object> filters
    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (filters == null || filters.isEmpty()) {
            return predicates;
        }

        filters.forEach((key, value) -> {

            if (value == null || value.toString().isBlank()) {
                return;
            }

            // -----------------------
            // LIKE
            // -----------------------

            if (key.endsWith(SearchConstants.LIKE)) {

                String field = key.replace(
                    SearchConstants.LIKE,
                    ""
                );
                validateField(root, field);

                predicates.add(
                    cb.like(
                        cb.lower(root.get(field)),
                        "%" + value.toString().toLowerCase() + "%"
                    )
                );

            }

            // -----------------------
            // IN
            // -----------------------

            else if (key.endsWith(SearchConstants.IN)) {

                String field = key.replace(
                    SearchConstants.IN,
                    ""
                );
                validateField(root, field);


                CriteriaBuilder.In<Object> in =
                    cb.in(root.get(field));

                for (String item : value.toString().split(",")) {
                    in.value(item.trim());
                }

                predicates.add(in);

            }

            // -----------------------
            // BETWEEN
            // -----------------------

            else if (key.endsWith(SearchConstants.BETWEEN)) {

                String field = key.replace(
                    SearchConstants.BETWEEN,
                    ""
                );
                validateField(root, field);

                String[] values =
                    value.toString().split(",");

                if (values.length == 2) {

                    predicates.add(
                        cb.between(
                            root.get(field).as(String.class),
                            values[0].trim(),
                            values[1].trim()
                        )
                    );
                }

            }

            // -----------------------
            // EQUALS
            // -----------------------

            else {
                validateField(root, key);
                predicates.add(
                    cb.equal(
                        root.get(key),
                        value
                    )
                );

            }

        });

        return predicates;
    }

    private <T> void validateField(
        Root<T> root,
        String field
    ) {

        try {
            root.get(field);
        } catch (IllegalArgumentException ex) {
            throw new InvalidSearchFieldException(field);
        }

    }

}

package com.derblaz.educational.institution.api.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {
    private SpecificationUtils() {}

    public static <T> Specification<T> like(final String prop, final String term){
        return ((root, query, cb) -> cb.like(cb.upper(root.get(prop)), like(term.toUpperCase())));
    }

    public static <T> Specification<T> active() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }
    private static String like (final String term){
        return "%" + term + "%";
    }
}

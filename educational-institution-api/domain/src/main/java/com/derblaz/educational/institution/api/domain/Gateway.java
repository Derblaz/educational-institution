package com.derblaz.educational.institution.api.domain;

import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;

import java.util.Optional;

public interface Gateway<T, ID> {
    T create(T anAggregate);

    void deleteById(ID anId);

    Optional<T> findById(ID anId);

    T update(T anAggregate);

    Pagination<T> findAll(SearchQuery aQuery);
}

package com.derblaz.educational.institution.api.application.discipline.retrieve.list;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;

import java.util.Objects;

public class ListDisciplinePagedUseCase implements UseCase<SearchQuery, Pagination<DisciplineListOutput>> {

    private final DisciplineGateway disciplineGateway;

    public ListDisciplinePagedUseCase(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public Pagination<DisciplineListOutput> execute(SearchQuery query) {
        return disciplineGateway.findAll(query)
                .map(DisciplineListOutput::from);
    }
}

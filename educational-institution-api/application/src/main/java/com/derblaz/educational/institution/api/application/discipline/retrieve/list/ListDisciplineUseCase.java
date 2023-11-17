package com.derblaz.educational.institution.api.application.discipline.retrieve.list;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;

import java.util.List;
import java.util.Objects;

public class ListDisciplineUseCase implements UseCase<ListDisciplineCommand, List<DisciplineListOutput>> {

    private final DisciplineGateway disciplineGateway;

    public ListDisciplineUseCase(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public List<DisciplineListOutput> execute(ListDisciplineCommand command) {
        return disciplineGateway.findByNameAndActive(command.search(), command.limit())
                .stream().map(DisciplineListOutput::from)
                .toList();
    }
}

package com.derblaz.educational.institution.api.application.discipline.retrieve.get;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;

import java.util.Objects;

public class GetDisciplineByIdUseCase implements UseCase<String, GetDisciplineOutput> {

    private final DisciplineGateway disciplineGateway;

    public GetDisciplineByIdUseCase(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public GetDisciplineOutput execute(String id) {
        final var disciplineID = DisciplineID.from(id);
        return disciplineGateway.findById(disciplineID)
                .map(GetDisciplineOutput::from)
                .orElseThrow(() -> NotFoundException.with(Discipline.class, disciplineID));
    }
}

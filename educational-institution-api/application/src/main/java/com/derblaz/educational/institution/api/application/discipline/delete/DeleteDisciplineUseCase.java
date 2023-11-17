package com.derblaz.educational.institution.api.application.discipline.delete;

import com.derblaz.educational.institution.api.application.UnitUseCase;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;

import java.util.Objects;

public class DeleteDisciplineUseCase implements UnitUseCase<String> {

    private final DisciplineGateway disciplineGateway;

    public DeleteDisciplineUseCase(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }


    @Override
    public void execute(String id) {
        this.disciplineGateway.deleteById(DisciplineID.from(id));
    }
}

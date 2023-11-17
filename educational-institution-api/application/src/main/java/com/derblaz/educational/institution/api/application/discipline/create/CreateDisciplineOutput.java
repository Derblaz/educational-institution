package com.derblaz.educational.institution.api.application.discipline.create;

import com.derblaz.educational.institution.api.domain.discipline.Discipline;

public record CreateDisciplineOutput(String id) {

    public static CreateDisciplineOutput from(Discipline discipline) {
        return new CreateDisciplineOutput(discipline.getId().getValue());
    }
}

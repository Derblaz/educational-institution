package com.derblaz.educational.institution.api.application.discipline.update;

import com.derblaz.educational.institution.api.domain.discipline.Discipline;

public record UpdateDisciplineOutput(String id) {

    public static UpdateDisciplineOutput from(Discipline aDiscipline){
        return new UpdateDisciplineOutput(aDiscipline.getId().getValue());
    }
}

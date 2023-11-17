package com.derblaz.educational.institution.api.application.discipline.create;

public record CreateDisciplineCommand(
        String name,
        double credits,
        String description,
        String program,
        boolean presential
) {
}

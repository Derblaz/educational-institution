package com.derblaz.educational.institution.api.application.discipline.update;

public record UpdateDisciplineCommand(
        String id,
        String name,
        double credits,
        String description,
        String program,
        boolean presential,
        boolean isActive
) {
}

package com.derblaz.educational.institution.api.infrastructure.discipline.models;

public record UpdateDisciplineRequest(
        String id,
        String name,
        double credits,
        String description,
        String program,
        Boolean presential,
        Boolean isActive
) {
}

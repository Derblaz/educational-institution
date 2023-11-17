package com.derblaz.educational.institution.api.infrastructure.discipline.models;

public record CreateDisciplineRequest(
        String name,
        double credits,
        String description,
        String program,
        Boolean presential
) {
}
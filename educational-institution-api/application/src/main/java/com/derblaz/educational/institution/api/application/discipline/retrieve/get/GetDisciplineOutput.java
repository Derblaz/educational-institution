package com.derblaz.educational.institution.api.application.discipline.retrieve.get;

import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;

import java.time.Instant;

public record GetDisciplineOutput(
        DisciplineID id,
        String name,
        double credits,
        String description,
        String program,
        boolean presential,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static GetDisciplineOutput from(Discipline discipline) {
        return new GetDisciplineOutput(
                discipline.getId(),
                discipline.getName(),
                discipline.getCredits(),
                discipline.getDescription(),
                discipline.getProgram(),
                discipline.isPresential(),
                discipline.isActive(),
                discipline.getCreatedAt(),
                discipline.getUpdatedAt(),
                discipline.getDeletedAt()
        );
    }
}

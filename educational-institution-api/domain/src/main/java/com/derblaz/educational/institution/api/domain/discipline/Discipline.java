package com.derblaz.educational.institution.api.domain.discipline;

import com.derblaz.educational.institution.api.domain.AggregateRoot;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.time.Instant;

public class Discipline extends AggregateRoot<Discipline, DisciplineID> {

    private String name;
    private double credits;
    private String description;
    private String program;
    private boolean presential;

    private Discipline(
            DisciplineID disciplineID,
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
        super(disciplineID, active, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.program = program;
        this.presential = presential;
        selfValidate();
    }

    public static Discipline newDiscipline(
            final String name,
            final double credits,
            final String description,
            final String program,
            final boolean presential
    ) {
        return new Discipline(
                DisciplineID.unique(),
                name,
                credits,
                description,
                program,
                presential,
                true,
                Instant.now(),
                Instant.now(),
                null
        );
    }

    public static Discipline with(
            final String id,
            final String name,
            final double credits,
            final String description,
            final String program,
            final boolean presential,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Discipline(
                DisciplineID.from(id),
                name,
                credits,
                description,
                program,
                presential,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Discipline update(
            final String name,
            final double credits,
            final String description,
            final String program,
            final boolean presential,
            final boolean isActive
    ) {
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.program = program;
        this.presential = presential;
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        this.validate(notification);

        if(notification.hasError()){
            throw new NotificationException("", notification);
        }
    }

    private void validate(ValidationHandler notification) {
        new DisciplineValidator(this, notification).validate();
    }

    public String getName() {
        return name;
    }

    public double getCredits() {
        return credits;
    }

    public String getDescription() {
        return description;
    }

    public String getProgram() {
        return program;
    }

    public boolean isPresential() {
        return presential;
    }
}

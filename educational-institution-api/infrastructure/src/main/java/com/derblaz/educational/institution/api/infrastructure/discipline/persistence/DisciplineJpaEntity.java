package com.derblaz.educational.institution.api.infrastructure.discipline.persistence;


import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "discipline")
public class DisciplineJpaEntity {
    @Id
    @Column(name ="id", nullable = false)
    private String id;
    @Column(name ="name", nullable = false)
    private String name;
    @Column(name ="credits", nullable = false)
    private double credits;
    @Column(name ="description")
    private String description;
    @Lob
    @Column(name ="program", columnDefinition = "TEXT")
    private String program;
    @Column(name = "presential", nullable = false)
    private boolean presential;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "createdAt", nullable = false)
    private Instant createdAt;
    @Column(name = "updatedAt", nullable = false)
    private Instant updatedAt;
    @Column(name = "deletedAt")
    private Instant deletedAt;

    public DisciplineJpaEntity() {
    }

    public DisciplineJpaEntity(String id,
                               String name,
                               double credits,
                               String description,
                               String program,
                               boolean presential,
                               boolean active,
                               Instant createdAt,
                               Instant updatedAt,
                               Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.program = program;
        this.presential = presential;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static DisciplineJpaEntity from(final Discipline aDiscipline){
        return new DisciplineJpaEntity(
                aDiscipline.getId().getValue(),
                aDiscipline.getName(),
                aDiscipline.getCredits(),
                aDiscipline.getDescription(),
                aDiscipline.getProgram(),
                aDiscipline.isPresential(),
                aDiscipline.isActive(),
                aDiscipline.getCreatedAt(),
                aDiscipline.getUpdatedAt(),
                aDiscipline.getDeletedAt()
        );
    }

    public Discipline toAggregate(){
        return Discipline.with(
                this.id,
                this.name,
                this.credits,
                this.description,
                this.program,
                this.presential,
                this.active,
                this.createdAt,
                this.updatedAt,
                this.deletedAt
        );
    }
}

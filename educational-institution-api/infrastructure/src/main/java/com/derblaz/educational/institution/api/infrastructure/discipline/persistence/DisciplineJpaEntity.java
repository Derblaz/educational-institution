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
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    @Column(name = "deleted_at")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public boolean isPresential() {
        return presential;
    }

    public void setPresential(boolean presential) {
        this.presential = presential;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}

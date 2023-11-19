package com.derblaz.educational.institution.api.infrastructure.course.persistence;

import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.infrastructure.discipline.persistence.DisciplineJpaEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "course")
public class CourseJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "months_duration", nullable = false)
    private int monthsDuration;
    @Column(name = "places", nullable = false)
    private int places;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CourseDisciplineJpaEntity> disciplines;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    @Column(name = "deleted_at")
    private Instant deletedAt;

    public CourseJpaEntity() {
    }

    private CourseJpaEntity(String id, String name, String description, int monthsDuration, int places, boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.monthsDuration = monthsDuration;
        this.places = places;
        this.active = active;
        this.disciplines = new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static CourseJpaEntity from(final Course aCourse){
        final var anEntity = new CourseJpaEntity(
                aCourse.getId().getValue(),
                aCourse.getName(),
                aCourse.getDescription(),
                aCourse.getMonthsDuration(),
                aCourse.getPlaces(),
                aCourse.isActive(),
                aCourse.getCreatedAt(),
                aCourse.getUpdatedAt(),
                aCourse.getDeletedAt()
        );

        anEntity.addSemesters(aCourse.getSemesters());
        return anEntity;
    }

    public Course toAggregate(){
        return Course.with(
                CourseID.from(getId()),
                this.name,
                this.description,
                this.monthsDuration,
                this.places,
                getSemesters(),
                this.active,
                this.createdAt,
                this.updatedAt,
                this.deletedAt
        );
    }

    private void addSemesters(List<List<Discipline>> semesters){
        for (int i = 0; i < semesters.size(); i++) {
            final var semesterIndex = i;
            final var semester = semesters.get(i);
            semester.forEach(disciplineID -> addDiscipline(semesterIndex, disciplineID));
        }
    }

    private void addDiscipline(int semesterIndex, Discipline discipline) {
        this.disciplines.add(new CourseDisciplineJpaEntity(this, semesterIndex, DisciplineJpaEntity.from(discipline)));
    }

    private void removeDiscipline(int semesterIndex, Discipline discipline) {
        this.disciplines.remove(new CourseDisciplineJpaEntity(this, semesterIndex, DisciplineJpaEntity.from(discipline)));
    }

    public List<List<Discipline>> getSemesters(){
        final var semestersSize = getDisciplines().stream()
                .collect(Collectors.groupingBy(CourseDisciplineJpaEntity::getSemesterIndex))
                .size();
        final var semesters = new ArrayList<List<Discipline>>(semestersSize);
        for (int i = 0; i < semestersSize; i++) {
            semesters.add(new ArrayList<>());
        }
        getDisciplines().forEach(disciplineJpaEntity -> {
            semesters
                    .get(disciplineJpaEntity.getSemesterIndex())
                    .add(disciplineJpaEntity.getDiscipline().toAggregate());
        });
        return semesters;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }

    public void setMonthsDuration(int monthsDuration) {
        this.monthsDuration = monthsDuration;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public List<CourseDisciplineJpaEntity> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<CourseDisciplineJpaEntity> disciplines) {
        this.disciplines = disciplines;
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

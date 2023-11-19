package com.derblaz.educational.institution.api.domain.course;

import com.derblaz.educational.institution.api.domain.AggregateRoot;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Course extends AggregateRoot<Course, CourseID> {

    private String name;
    private String description;
    private int monthsDuration;
    private int places;
    private List<List<Discipline>> semesters;

    private Course(CourseID courseID,
                  String name,
                  String description,
                  int monthsDuration,
                  int places,
                  List<List<Discipline>> semesters,
                  boolean active,
                  Instant createdAt,
                  Instant updatedAt,
                  Instant deletedAt
    ) {
        super(courseID, active, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.description = description;
        this.monthsDuration = monthsDuration;
        this.places = places;
        this.semesters = new ArrayList<>(semesters);
        selfValidate();
    }

    public static Course newCourse(
            String name,
            String description,
            int monthsDuration,
            int places
    ){
        return new Course(
                CourseID.unique(),
                name,
                description,
                monthsDuration,
                places,
                new ArrayList<>(),
                true,
                Instant.now(),
                Instant.now(),
                null
        );
    }

    public static Course with(
            CourseID courseID,
            String name,
            String description,
            int monthsDuration,
            int places,
            List<List<Discipline>> semesters,
            boolean active,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ){
        return new Course(
                courseID,
                name,
                description,
                monthsDuration,
                places,
                semesters,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public Course update(
            String name,
            String description,
            int monthsDuration,
            int places,
            boolean isActive,
            List<List<Discipline>> semesters
    ){
        if(isActive){
            activate();
        } else {
            deactivate();
        }
        this.name = name;
        this.description = description;
        this.monthsDuration = monthsDuration;
        this.places = places;
        this.semesters = new ArrayList<>(Objects.requireNonNullElse(semesters, Collections.emptyList()));
        selfValidate();
        return this;
    }

    public Course addSemesters(List<List<Discipline>> semesters){
        if(Objects.isNull(semesters) || semesters.isEmpty()){
            return this;
        }

        for (int i = 0; i < semesters.size(); i++) {
            addDisciplines(i, semesters.get(i));
        }

        return this;
    }

    public Course addDiscipline(int semesterIndex, Discipline disciplineID){
        if (semesterIndex < 0 || disciplineID == null){
            return this;
        }

        if(hasIndex(semesterIndex)){
            semesters.get(semesterIndex).add(disciplineID);
        } else {
            semesters.add(new ArrayList<>(List.of(disciplineID)));
        }
        return this;
    }

    public Course addDisciplines(int semesterIndex, List<Discipline> disciplines){
        if (Objects.isNull(disciplines) || disciplines.isEmpty()){
            return this;
        }

        if(hasIndex(semesterIndex)){
            semesters.get(semesterIndex).addAll(disciplines);
        } else {
            semesters.add(new ArrayList<>(disciplines));
        }
        return this;
    }

    public Course removeDisclipline(int semesterIndex, Discipline discipline){
        if(semesterIndex < 0 || discipline == null){
            return this;
        }
        if(hasIndex(semesterIndex)) semesters.get(semesterIndex).remove(discipline);
        return this;
    }

    private boolean hasIndex(int semesterIndex){
        return semesterIndex < getSemesters().size();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        this.validate(notification);

        if(notification.hasError()){
            throw new NotificationException("Discipline has error", notification);
        }
    }

    private void validate(ValidationHandler notification) {
        new CourseValidator(this, notification).validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPlaces() {
        return places;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }

    public List<List<Discipline>> getSemesters() {
        return semesters;
    }
}

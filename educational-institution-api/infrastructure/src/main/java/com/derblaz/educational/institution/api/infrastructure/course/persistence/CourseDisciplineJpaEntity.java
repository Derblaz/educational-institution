package com.derblaz.educational.institution.api.infrastructure.course.persistence;

import com.derblaz.educational.institution.api.infrastructure.discipline.persistence.DisciplineJpaEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "courses_disciplines")
public class CourseDisciplineJpaEntity {

    @EmbeddedId
    private CourseDisciplineID id;

    @ManyToOne
    @MapsId("courseId")
    private CourseJpaEntity course;

    @ManyToOne
    @MapsId("disciplineId")
    private DisciplineJpaEntity discipline;

    @Column(name = "semester_index")
    private int semesterIndex;

    public CourseDisciplineJpaEntity() {
    }

    public CourseDisciplineJpaEntity(CourseJpaEntity course, int semesterIndex, DisciplineJpaEntity discipline) {
        this.id = CourseDisciplineID.from(course.getId(), discipline.getId());
        this.semesterIndex = semesterIndex;
        this.course = course;
        this.discipline = discipline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDisciplineJpaEntity that = (CourseDisciplineJpaEntity) o;
        return semesterIndex == that.semesterIndex && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semesterIndex);
    }

    public CourseDisciplineID getId() {
        return id;
    }

    public void setId(CourseDisciplineID id) {
        this.id = id;
    }

    public CourseJpaEntity getCourse() {
        return course;
    }

    public void setCourse(CourseJpaEntity course) {
        this.course = course;
    }

    public int getSemesterIndex() {
        return semesterIndex;
    }

    public void setSemesterIndex(int semesterIndex) {
        this.semesterIndex = semesterIndex;
    }

    public DisciplineJpaEntity getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineJpaEntity discipline) {
        this.discipline = discipline;
    }
}

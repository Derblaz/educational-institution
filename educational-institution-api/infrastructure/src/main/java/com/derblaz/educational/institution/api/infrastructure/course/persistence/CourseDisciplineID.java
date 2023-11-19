package com.derblaz.educational.institution.api.infrastructure.course.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CourseDisciplineID implements Serializable {

    @Column(name = "course_id", nullable = false)
    private String courseId;
    @Column(name = "discipline_id", nullable = false)
    private String disciplineId;

    public CourseDisciplineID() {
    }

    public CourseDisciplineID(String courseId, String disciplineId) {
        this.courseId = courseId;
        this.disciplineId = disciplineId;
    }

    public static CourseDisciplineID from(String courseId, String disciplineId){
        return new CourseDisciplineID(courseId, disciplineId);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId;
    }
}

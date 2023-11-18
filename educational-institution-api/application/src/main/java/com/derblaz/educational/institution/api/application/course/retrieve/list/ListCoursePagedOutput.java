package com.derblaz.educational.institution.api.application.course.retrieve.list;

import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;

import java.time.Instant;
import java.util.List;

public record ListCoursePagedOutput(
        CourseID courseID,
        String name,
        String description,
        int monthsDuration,
        int places,
        List<List<DisciplineID>> semesters,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static ListCoursePagedOutput from(Course aCourse){
        return new ListCoursePagedOutput(
                aCourse.getId(),
                aCourse.getName(),
                aCourse.getDescription(),
                aCourse.getMonthsDuration(),
                aCourse.getPlaces(),
                aCourse.getSemesters(),
                aCourse.isActive(),
                aCourse.getCreatedAt(),
                aCourse.getUpdatedAt(),
                aCourse.getDeletedAt()
        );
    }
}

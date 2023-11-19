package com.derblaz.educational.institution.api.application.course.retrieve.get;

import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;

import java.time.Instant;
import java.util.List;

public record GetCourseOutput(
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
) {
    public static GetCourseOutput from(Course aCourse){
        return new GetCourseOutput(
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

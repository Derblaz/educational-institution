package com.derblaz.educational.institution.api.application.course.create;

import com.derblaz.educational.institution.api.domain.course.Course;

public record CreateCourseOutput(String id) {
    public static CreateCourseOutput from(Course course){
        return new CreateCourseOutput(course.getId().getValue());
    }
}

package com.derblaz.educational.institution.api.application.course.update;

import com.derblaz.educational.institution.api.domain.course.Course;

public record UpdateCourseOutput(String id) {
    public static UpdateCourseOutput from(Course course){
        return new UpdateCourseOutput(course.getId().getValue());
    }
}

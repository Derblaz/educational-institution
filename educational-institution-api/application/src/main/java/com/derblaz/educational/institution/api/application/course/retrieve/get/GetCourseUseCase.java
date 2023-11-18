package com.derblaz.educational.institution.api.application.course.retrieve.get;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;

import java.util.Objects;

public class GetCourseUseCase implements UseCase<String, GetCourseOutput> {

    private final CourseGateway courseGateway;

    public GetCourseUseCase(CourseGateway courseGateway) {
        this.courseGateway = Objects.requireNonNull(courseGateway);
    }

    @Override
    public GetCourseOutput execute(String id) {
        final var disciplineID = CourseID.from(id);
        return courseGateway.findById(disciplineID)
                .map(GetCourseOutput::from)
                .orElseThrow(() -> NotFoundException.with(Course.class, disciplineID));
    }
}

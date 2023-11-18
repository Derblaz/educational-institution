package com.derblaz.educational.institution.api.application.course.delete;

import com.derblaz.educational.institution.api.application.UnitUseCase;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.course.CourseID;

import java.util.Objects;

public class DeleteCourseUseCase implements UnitUseCase<String> {

    private final CourseGateway courseGateway;

    public DeleteCourseUseCase(CourseGateway courseGateway) {
        this.courseGateway = Objects.requireNonNull(courseGateway);
    }

    @Override
    public void execute(String aCommand) {
        this.courseGateway.deleteById(CourseID.from(aCommand));
    }
}

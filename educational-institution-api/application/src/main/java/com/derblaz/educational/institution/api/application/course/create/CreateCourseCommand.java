package com.derblaz.educational.institution.api.application.course.create;

import java.util.List;

public record CreateCourseCommand(
        String name,
        String description,
        int monthsDuration,
        int places,
        List<List<String>> semesters
) {

}

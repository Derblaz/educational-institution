package com.derblaz.educational.institution.api.application.course.update;

import java.util.List;

public record UpdateCourseCommand(
        String id,
        String name,
        String description,
        int monthsDuration,
        int places,
        boolean active,
        List<List<String>> semesters
) {
}

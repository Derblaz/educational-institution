package com.derblaz.educational.institution.api.infrastructure.course.controllers;

import java.util.List;

public record UpdateCourseRequest(
        String name,
        String description,
        int monthsDuration,
        int places,
        Boolean active,
        List<List<String>> semesters
) {
}

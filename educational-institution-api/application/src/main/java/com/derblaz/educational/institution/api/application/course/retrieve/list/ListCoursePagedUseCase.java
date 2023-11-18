package com.derblaz.educational.institution.api.application.course.retrieve.list;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;

import java.util.Objects;

public class ListCoursePagedUseCase implements UseCase<SearchQuery, Pagination<ListCoursePagedOutput>> {

    private final CourseGateway courseGateway;

    public ListCoursePagedUseCase(CourseGateway courseGateway) {
        this.courseGateway = Objects.requireNonNull(courseGateway);
    }

    @Override
    public Pagination<ListCoursePagedOutput> execute(SearchQuery query) {
        return courseGateway.findAll(query)
                .map(ListCoursePagedOutput::from);
    }
}

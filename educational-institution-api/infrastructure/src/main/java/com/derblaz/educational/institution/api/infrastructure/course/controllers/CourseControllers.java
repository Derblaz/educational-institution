package com.derblaz.educational.institution.api.infrastructure.course.controllers;

import com.derblaz.educational.institution.api.application.course.create.CreateCourseCommand;
import com.derblaz.educational.institution.api.application.course.create.CreateCourseOutput;
import com.derblaz.educational.institution.api.application.course.create.CreateCourseUseCase;
import com.derblaz.educational.institution.api.application.course.delete.DeleteCourseUseCase;
import com.derblaz.educational.institution.api.application.course.retrieve.get.GetCourseOutput;
import com.derblaz.educational.institution.api.application.course.retrieve.get.GetCourseUseCase;
import com.derblaz.educational.institution.api.application.course.retrieve.list.ListCoursePagedOutput;
import com.derblaz.educational.institution.api.application.course.retrieve.list.ListCoursePagedUseCase;
import com.derblaz.educational.institution.api.application.course.update.UpdateCourseCommand;
import com.derblaz.educational.institution.api.application.course.update.UpdateCourseOutput;
import com.derblaz.educational.institution.api.application.course.update.UpdateCourseUseCase;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.infrastructure.api.CourseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CourseControllers implements CourseAPI {

    private final CreateCourseUseCase createCourseUseCase;
    private final GetCourseUseCase getCourseUseCase;
    private final ListCoursePagedUseCase listCoursePagedUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;

    public CourseControllers(CreateCourseUseCase createCourseUseCase, GetCourseUseCase getCourseUseCase, ListCoursePagedUseCase listCoursePagedUseCase, UpdateCourseUseCase updateCourseUseCase, DeleteCourseUseCase deleteCourseUseCase) {
        this.createCourseUseCase = Objects.requireNonNull(createCourseUseCase);
        this.getCourseUseCase = Objects.requireNonNull(getCourseUseCase);
        this.listCoursePagedUseCase = Objects.requireNonNull(listCoursePagedUseCase);
        this.updateCourseUseCase = Objects.requireNonNull(updateCourseUseCase);
        this.deleteCourseUseCase = Objects.requireNonNull(deleteCourseUseCase);
    }


    @Override
    public ResponseEntity<CreateCourseOutput> createCourse(CreateCourseCommand command) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createCourseUseCase.execute(command));
    }

    @Override
    public ResponseEntity<GetCourseOutput> getCourse(String id) {
        return ResponseEntity
                .ok(getCourseUseCase.execute(id));
    }

    @Override
    public ResponseEntity<Pagination<ListCoursePagedOutput>> lisCoursePaged(String search, int page, int perPage, String sort, String dir) {
        final var query = new SearchQuery(
                page,
                perPage,
                search,
                sort,
                dir
        );

        return ResponseEntity
                .ok(listCoursePagedUseCase.execute(query));
    }

    @Override
    public ResponseEntity<UpdateCourseOutput> updateCourseById(String id, UpdateCourseRequest request) {
        final var command = new UpdateCourseCommand(
                id,
                request.name(),
                request.description(),
                request.monthsDuration(),
                request.places(),
                Objects.requireNonNullElse(request.active(), true),
                request.semesters()
        );
        return ResponseEntity
                .ok(updateCourseUseCase.execute(command));
    }

    @Override
    public ResponseEntity<Void> deleteCoursebyId(String id) {
        deleteCourseUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

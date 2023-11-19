package com.derblaz.educational.institution.api.infrastructure.api;

import com.derblaz.educational.institution.api.application.course.create.CreateCourseCommand;
import com.derblaz.educational.institution.api.application.course.create.CreateCourseOutput;
import com.derblaz.educational.institution.api.application.course.retrieve.get.GetCourseOutput;
import com.derblaz.educational.institution.api.application.course.retrieve.list.ListCoursePagedOutput;
import com.derblaz.educational.institution.api.application.course.update.UpdateCourseOutput;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.infrastructure.course.controllers.UpdateCourseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("courses")
@Tag(name = "Courses")
public interface CourseAPI {

    @PostMapping
    @Operation(summary = "Cria um novo Curso.")
    ResponseEntity<CreateCourseOutput> createCourse(@RequestBody CreateCourseCommand command);

    @GetMapping("/{id}")
    @Operation(summary = "Obtem um Curso pelo seu ID.")
    ResponseEntity<GetCourseOutput> getCourse(@PathVariable String id);

    @GetMapping()
    @Operation(summary = "Obtem uma página de Curso.")
    ResponseEntity<Pagination<ListCoursePagedOutput>> lisCoursePaged(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir
    );

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um Curso pelo seu ID.")
    ResponseEntity<UpdateCourseOutput> updateCourseById(@PathVariable String id, @RequestBody UpdateCourseRequest request);

    @DeleteMapping("{id}")
    @Operation(summary = "Exclui um Curso pelo seu ID.")
    ResponseEntity<Void> deleteCoursebyId(@PathVariable String id);
}

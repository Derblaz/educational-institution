package com.derblaz.educational.institution.api.infrastructure.discipline.controllers;

import com.derblaz.educational.institution.api.application.discipline.create.CreateDisciplineCommand;
import com.derblaz.educational.institution.api.application.discipline.create.CreateDisciplineOutput;
import com.derblaz.educational.institution.api.application.discipline.create.CreateDisciplineUseCase;
import com.derblaz.educational.institution.api.application.discipline.delete.DeleteDisciplineUseCase;
import com.derblaz.educational.institution.api.application.discipline.retrieve.get.GetDisciplineByIdUseCase;
import com.derblaz.educational.institution.api.application.discipline.retrieve.get.GetDisciplineOutput;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.DisciplineListOutput;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.ListDisciplineCommand;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.ListDisciplinePagedUseCase;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.ListDisciplineUseCase;
import com.derblaz.educational.institution.api.application.discipline.update.UpdateDisciplineCommand;
import com.derblaz.educational.institution.api.application.discipline.update.UpdateDisciplineOutput;
import com.derblaz.educational.institution.api.application.discipline.update.UpdateDisciplineUseCase;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.infrastructure.api.DisciplineAPI;
import com.derblaz.educational.institution.api.infrastructure.discipline.models.CreateDisciplineRequest;
import com.derblaz.educational.institution.api.infrastructure.discipline.models.UpdateDisciplineRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class DiscliplineController implements DisciplineAPI {

    private final CreateDisciplineUseCase createDisciplineUseCase;
    private final GetDisciplineByIdUseCase getDisciplineByIdUseCase;
    private final ListDisciplinePagedUseCase listDisciplinePagedUseCase;
    private final ListDisciplineUseCase listDisciplineUseCase;
    private final UpdateDisciplineUseCase updateDisciplineUseCase;
    private final DeleteDisciplineUseCase deleteDisciplineUseCase;

    public DiscliplineController(CreateDisciplineUseCase createDisciplineUseCase, GetDisciplineByIdUseCase getDisciplineByIdUseCase, ListDisciplinePagedUseCase listDisciplinePagedUseCase, ListDisciplineUseCase listDisciplineUseCase, UpdateDisciplineUseCase updateDisciplineUseCase, DeleteDisciplineUseCase deleteDisciplineUseCase) {
        this.createDisciplineUseCase = Objects.requireNonNull(createDisciplineUseCase);
        this.getDisciplineByIdUseCase = Objects.requireNonNull(getDisciplineByIdUseCase);
        this.listDisciplinePagedUseCase = Objects.requireNonNull(listDisciplinePagedUseCase);
        this.listDisciplineUseCase = Objects.requireNonNull(listDisciplineUseCase);
        this.updateDisciplineUseCase = Objects.requireNonNull(updateDisciplineUseCase);
        this.deleteDisciplineUseCase = Objects.requireNonNull(deleteDisciplineUseCase);
    }

    @Override
    public ResponseEntity<CreateDisciplineOutput> createDiscipline(CreateDisciplineRequest request) {
        final var commnad = new CreateDisciplineCommand(
                request.name(),
                request.credits(),
                request.description(),
                request.program(),
                Objects.requireNonNullElse(request.presential(), true)
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createDisciplineUseCase.execute(commnad));
    }

    @Override
    public ResponseEntity<GetDisciplineOutput> getDisciplineById(String id) {
        return ResponseEntity.ok(getDisciplineByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<Pagination<DisciplineListOutput>> lisDisciplinePaged(String search, int page, int perPage, String sort, String dir) {
        final var query = new SearchQuery(
                page,
                perPage,
                search,
                sort,
                dir
        );
        return ResponseEntity.ok(listDisciplinePagedUseCase.execute(query));
    }

    @Override
    public ResponseEntity<List<DisciplineListOutput>> listDiscipline(String search, int perPage) {
        final var query = new ListDisciplineCommand(search, perPage);
        return ResponseEntity.ok(listDisciplineUseCase.execute(query));
    }

    @Override
    public ResponseEntity<UpdateDisciplineOutput> updateDisciplineById(String id, UpdateDisciplineRequest request) {
        final var command = new UpdateDisciplineCommand(
                id,
                request.name(),
                request.credits(),
                request.description(),
                request.program(),
                Objects.requireNonNullElse(request.presential(), true),
                Objects.requireNonNullElse(request.isActive(), true)
        );
        return ResponseEntity.ok(updateDisciplineUseCase.execute(command));
    }

    @Override
    public ResponseEntity<Void> deleteDisciplinebyId(String id) {
        deleteDisciplineUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}

package com.derblaz.educational.institution.api.infrastructure.api;

import com.derblaz.educational.institution.api.application.discipline.create.CreateDisciplineOutput;
import com.derblaz.educational.institution.api.application.discipline.retrieve.get.GetDisciplineOutput;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.DisciplineListOutput;
import com.derblaz.educational.institution.api.application.discipline.update.UpdateDisciplineOutput;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.infrastructure.discipline.models.CreateDisciplineRequest;
import com.derblaz.educational.institution.api.infrastructure.discipline.models.UpdateDisciplineRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("disciplines")
@Tag(name = "Disciplines")
public interface DisciplineAPI {

    @PostMapping
    @Operation(summary = "Cria uma nova Disciplina.")
    ResponseEntity<CreateDisciplineOutput> createDiscipline(@RequestBody CreateDisciplineRequest request);

    @GetMapping("/{id}")
    @Operation(summary = "Obtem uma Disciplina pelo seu ID.")
    ResponseEntity<GetDisciplineOutput> getDisciplineById(@PathVariable String id);

    @GetMapping()
    @Operation(summary = "Obtem uma página de Disciplinas.")
    ResponseEntity<Pagination<DisciplineListOutput>> lisDisciplinePaged(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir
    );

    @GetMapping("/firsts")
    ResponseEntity<List<DisciplineListOutput>> listDiscipline(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage
    );

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma Disciplina pelo seu ID.")
    ResponseEntity<UpdateDisciplineOutput> updateDisciplineById(@PathVariable String id, @RequestBody UpdateDisciplineRequest request);

    @DeleteMapping("{id}")
    @Operation(summary = "Exclui uma Disciplina pelo seu ID.")
    ResponseEntity<Void> deleteDisciplinebyId(@PathVariable String id);
}

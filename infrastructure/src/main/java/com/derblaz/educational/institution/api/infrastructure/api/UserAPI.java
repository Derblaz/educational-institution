package com.derblaz.educational.institution.api.infrastructure.api;

import com.derblaz.educational.institution.api.application.user.create.CreateUserCommand;
import com.derblaz.educational.institution.api.application.user.create.CreateUserOutput;
import com.derblaz.educational.institution.api.application.user.retrieve.get.UserOutput;
import com.derblaz.educational.institution.api.application.user.retrieve.list.UserListOutput;
import com.derblaz.educational.institution.api.application.user.update.UpdateUserOutput;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.infrastructure.user.model.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("users")
@Tag(name = "Users")
public interface UserAPI {

    @PostMapping
    @Operation(summary = "Cria um novo usuário.")
    ResponseEntity<CreateUserOutput> createUser(@RequestBody CreateUserCommand command);

    @GetMapping("/{id}")
    @Operation(summary = "Obtem um usuário pelo seu ID.")
    ResponseEntity<UserOutput> getUserById(@PathVariable String id);

    @GetMapping()
    @Operation(summary = "Obtem uma lista de usuários.")
    ResponseEntity<Pagination<UserListOutput>> listUser(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir
    );

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário pelo seu ID.")
    ResponseEntity<UpdateUserOutput> updateUserById(@PathVariable String id, @RequestBody UpdateUserRequest request);

    @DeleteMapping("{id}")
    @Operation(summary = "Exclui um usuário pelo seu ID.")
    ResponseEntity<Void> deleteUserbyId(@PathVariable String id);


}

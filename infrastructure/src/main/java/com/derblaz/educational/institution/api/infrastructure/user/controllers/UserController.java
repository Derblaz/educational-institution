package com.derblaz.educational.institution.api.infrastructure.user.controllers;

import com.derblaz.educational.institution.api.application.user.create.CreateUserCommand;
import com.derblaz.educational.institution.api.application.user.create.CreateUserOutput;
import com.derblaz.educational.institution.api.application.user.create.CreateUserUseCase;
import com.derblaz.educational.institution.api.application.user.delete.DeleteUserUseCase;
import com.derblaz.educational.institution.api.application.user.retrieve.get.GetUserByIdUseCase;
import com.derblaz.educational.institution.api.application.user.retrieve.get.UserOutput;
import com.derblaz.educational.institution.api.application.user.retrieve.list.ListUserUseCase;
import com.derblaz.educational.institution.api.application.user.retrieve.list.UserListOutput;
import com.derblaz.educational.institution.api.application.user.update.UpdateUserCommand;
import com.derblaz.educational.institution.api.application.user.update.UpdateUserOutput;
import com.derblaz.educational.institution.api.application.user.update.UpdateUserUseCase;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.infrastructure.api.UserAPI;
import com.derblaz.educational.institution.api.infrastructure.user.model.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController implements UserAPI {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ListUserUseCase listUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserByIdUseCase getUserByIdUseCase, ListUserUseCase listUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = Objects.requireNonNull(createUserUseCase);
        this.getUserByIdUseCase = Objects.requireNonNull(getUserByIdUseCase);
        this.listUserUseCase = Objects.requireNonNull(listUserUseCase);
        this.updateUserUseCase = Objects.requireNonNull(updateUserUseCase);
        this.deleteUserUseCase = Objects.requireNonNull(deleteUserUseCase);
    }

    @Override
    public ResponseEntity<CreateUserOutput> createUser(CreateUserCommand command) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createUserUseCase.execute(command));
    }

    @Override
    public ResponseEntity<UserOutput> getUserById(String id) {
        return ResponseEntity.ok(getUserByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<Pagination<UserListOutput>> listUser(String search, int page, int perPage, String sort, String dir) {
        final var query = new SearchQuery(
                page,
                perPage,
                search,
                sort,
                dir
        );
        return ResponseEntity.ok(listUserUseCase.execute(query));
    }

    @Override
    public ResponseEntity<UpdateUserOutput> updateUserById(String id, UpdateUserRequest request) {
        final var command = UpdateUserCommand.with(
                id,
                request.name(),
                request.profile(),
                request.username(),
                request.password(),
                Objects.requireNonNullElse(request.isActive(), true)
        );
        return ResponseEntity.ok(updateUserUseCase.execute(command));
    }

    @Override
    public ResponseEntity<Void> deleteUserbyId(String id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}

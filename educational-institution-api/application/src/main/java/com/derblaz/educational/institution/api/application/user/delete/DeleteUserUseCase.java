package com.derblaz.educational.institution.api.application.user.delete;

import com.derblaz.educational.institution.api.application.UnitUseCase;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;

import java.util.Objects;

public class DeleteUserUseCase implements UnitUseCase<String> {

    private final UserGateway userGateway;

    public DeleteUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public void execute(String id) {
        final var userId = UserID.from(id);
        userGateway.deleteById(userId);
    }
}

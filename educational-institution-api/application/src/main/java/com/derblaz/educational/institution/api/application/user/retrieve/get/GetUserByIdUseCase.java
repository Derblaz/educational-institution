package com.derblaz.educational.institution.api.application.user.retrieve.get;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;

import java.util.Objects;

public class GetUserByIdUseCase implements UseCase<String, UserOutput> {

    private final UserGateway userGateway;

    public GetUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public UserOutput execute(String aCommand) {
        final var userId = UserID.from(aCommand);
        return userGateway.findById(userId)
                .map(UserOutput::from)
                .orElseThrow(() -> NotFoundException.with(User.class, userId));
    }
}

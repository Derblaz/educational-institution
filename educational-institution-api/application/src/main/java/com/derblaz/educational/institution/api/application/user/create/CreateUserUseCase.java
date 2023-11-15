package com.derblaz.educational.institution.api.application.user.create;


import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.Objects;

public class CreateUserUseCase implements UseCase<CreateUserCommand, CreateUserOutput> {

    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public CreateUserOutput execute(CreateUserCommand aCommand) {

        final var notification = Notification.create();
        final var user = notification
                .validate(() -> userGateway.create(
                        User.newUser(aCommand.name(), aCommand.profile(), aCommand.username(), aCommand.password())
                ));

        if(notification.hasError()){
            throw new NotificationException("Cloud not create User", notification);
        }

        return CreateUserOutput.from(user);
    }
}

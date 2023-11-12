package com.derblaz.educational.institution.api.application.user.update;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.Objects;

public class UpdateUserUseCase implements UseCase<UpdateUserCommand, UpdateUserOutput> {

    private final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public UpdateUserOutput execute(UpdateUserCommand aCommand) {
        final var userId = UserID.from(aCommand.id());
        var aUser = userGateway.findById(userId)
                .orElseThrow(() -> NotFoundException.with(User.class, userId))
                .update(
                    aCommand.name(),
                    aCommand.profile(),
                    aCommand.username(),
                    aCommand.password(),
                    aCommand.isActive()
                );

        final var notification = Notification.create();
        final var user = notification.validate(() -> userGateway.update(aUser));
        
        if(notification.hasError())
            throw new NotificationException("Could not update User", notification);

        return UpdateUserOutput.from(user);
    }
}

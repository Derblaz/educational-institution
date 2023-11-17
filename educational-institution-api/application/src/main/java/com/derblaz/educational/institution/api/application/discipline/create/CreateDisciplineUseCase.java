package com.derblaz.educational.institution.api.application.discipline.create;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.Objects;

public class CreateDisciplineUseCase implements UseCase<CreateDisciplineCommand, CreateDisciplineOutput> {

    private final DisciplineGateway disciplineGateway;

    public CreateDisciplineUseCase(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public CreateDisciplineOutput execute(CreateDisciplineCommand aCommand) {
        final var notification = Notification.create();
        final var discipline = notification
                .validate(() -> disciplineGateway.create(
                        Discipline.newDiscipline(aCommand.name(), aCommand.credits(), aCommand.description(), aCommand.program(), aCommand.presential())
                ));

        if(notification.hasError()){
            throw new NotificationException("Cloud not create User", notification);
        }

        return CreateDisciplineOutput.from(discipline);

    }
}

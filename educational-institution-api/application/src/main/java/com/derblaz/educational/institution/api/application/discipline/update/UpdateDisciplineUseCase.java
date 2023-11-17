package com.derblaz.educational.institution.api.application.discipline.update;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.Objects;

public class UpdateDisciplineUseCase implements UseCase<UpdateDisciplineCommand, UpdateDisciplineOutput> {

    private final DisciplineGateway disciplineGateway;

    public UpdateDisciplineUseCase(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public UpdateDisciplineOutput execute(UpdateDisciplineCommand aCommand) {
        final var disciplineId = DisciplineID.from(aCommand.id());
        var aDiscipline = disciplineGateway.findById(disciplineId)
                .orElseThrow(() -> NotFoundException.with(Discipline.class, disciplineId))
                .update(
                        aCommand.name(),
                        aCommand.credits(),
                        aCommand.description(),
                        aCommand.program(),
                        aCommand.presential(),
                        aCommand.isActive()
                );
        final var notification = Notification.create();
        final var discipline = notification.validate(() -> disciplineGateway.update(aDiscipline));

        if(notification.hasError())
            throw new NotificationException("Could not update Discipline", notification);

        return UpdateDisciplineOutput.from(discipline);
    }
}

package com.derblaz.educational.institution.api.infrastructure.configuration.usecases;

import com.derblaz.educational.institution.api.application.discipline.create.CreateDisciplineUseCase;
import com.derblaz.educational.institution.api.application.discipline.delete.DeleteDisciplineUseCase;
import com.derblaz.educational.institution.api.application.discipline.retrieve.get.GetDisciplineByIdUseCase;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.ListDisciplinePagedUseCase;
import com.derblaz.educational.institution.api.application.discipline.retrieve.list.ListDisciplineUseCase;
import com.derblaz.educational.institution.api.application.discipline.update.UpdateDisciplineUseCase;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class DisciplineUseCaseConfig {

    private final DisciplineGateway disciplineGateway;

    public DisciplineUseCaseConfig(DisciplineGateway disciplineGateway) {
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Bean
    public CreateDisciplineUseCase createDisciplineUseCase() {
        return new CreateDisciplineUseCase(disciplineGateway);
    }
    @Bean
    public GetDisciplineByIdUseCase getDisciplineByIdUseCase() {
        return new GetDisciplineByIdUseCase(disciplineGateway);
    }
    @Bean
    public ListDisciplinePagedUseCase listDisciplinePagedUseCase() {
        return new ListDisciplinePagedUseCase(disciplineGateway);
    }
    @Bean
    public ListDisciplineUseCase listDisciplineUseCase() {
        return new ListDisciplineUseCase(disciplineGateway);
    }
    @Bean
    public UpdateDisciplineUseCase updateDisciplineUseCase() {
        return new UpdateDisciplineUseCase(disciplineGateway);
    }
    @Bean
    public DeleteDisciplineUseCase deleteDisciplineUseCase() {
        return new DeleteDisciplineUseCase(disciplineGateway);
    }

}

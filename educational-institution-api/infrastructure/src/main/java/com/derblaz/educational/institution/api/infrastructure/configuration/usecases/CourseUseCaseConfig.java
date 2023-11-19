package com.derblaz.educational.institution.api.infrastructure.configuration.usecases;

import com.derblaz.educational.institution.api.application.course.create.CreateCourseUseCase;
import com.derblaz.educational.institution.api.application.course.delete.DeleteCourseUseCase;
import com.derblaz.educational.institution.api.application.course.retrieve.get.GetCourseUseCase;
import com.derblaz.educational.institution.api.application.course.retrieve.list.ListCoursePagedUseCase;
import com.derblaz.educational.institution.api.application.course.update.UpdateCourseUseCase;
import com.derblaz.educational.institution.api.infrastructure.course.persistence.CourseMySQLGateway;
import com.derblaz.educational.institution.api.infrastructure.discipline.persistence.DisciplineMySQLGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CourseUseCaseConfig {

    private final CourseMySQLGateway courseMySQLGateway;
    private final DisciplineMySQLGateway disciplineMySQLGateway;


    public CourseUseCaseConfig(CourseMySQLGateway courseMySQLGateway, DisciplineMySQLGateway disciplineMySQLGateway) {
        this.courseMySQLGateway = Objects.requireNonNull(courseMySQLGateway);
        this.disciplineMySQLGateway = Objects.requireNonNull(disciplineMySQLGateway);
    }

    @Bean
    public CreateCourseUseCase createCourseUseCase(){
        return new CreateCourseUseCase(courseMySQLGateway, disciplineMySQLGateway);
    }
    @Bean
    public GetCourseUseCase getCourseUseCase(){
        return new GetCourseUseCase(courseMySQLGateway);
    }
    @Bean
    public ListCoursePagedUseCase listCoursePagedUseCase(){
        return new ListCoursePagedUseCase(courseMySQLGateway);
    }
    @Bean
    public UpdateCourseUseCase updateCourseUseCase(){
        return new UpdateCourseUseCase(courseMySQLGateway, disciplineMySQLGateway);
    }
    @Bean
    public DeleteCourseUseCase deleteCourseUseCase(){
        return new DeleteCourseUseCase(courseMySQLGateway);
    }
}

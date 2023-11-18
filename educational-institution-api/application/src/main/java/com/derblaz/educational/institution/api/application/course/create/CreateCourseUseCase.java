package com.derblaz.educational.institution.api.application.course.create;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateCourseUseCase implements UseCase<CreareCourseCommand, CreateCourseOutput> {

    private final CourseGateway courseGateway;
    private final DisciplineGateway disciplineGateway;

    public CreateCourseUseCase(CourseGateway courseGateway, DisciplineGateway disciplineGateway) {
        this.courseGateway = Objects.requireNonNull(courseGateway);
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public CreateCourseOutput execute(CreareCourseCommand aCommand) {
        final var semesters = toSemesters(aCommand.semesters());
        final var notification = Notification.create();
        notification.append(validateDisciplines(semesters));
        final var course = notification.validate(() ->
                    courseGateway.create(
                            Course.newCourse(
                                    aCommand.name(),
                                    aCommand.description(),
                                    aCommand.monthsDuration(),
                                    aCommand.places()
                            )
                    )
                );

        if(notification.hasError()){
            throw new NotificationException("Cloud not create Course", notification);
        }
        course.addSemesters(semesters);
        return CreateCourseOutput.from(course);
    }

    private ValidationHandler validateDisciplines(List<List<DisciplineID>> semesters) {
        final var notification = Notification.create();
        if(Objects.isNull(semesters) || semesters.isEmpty())
            return notification;

        final var disciplines = semesters.stream()
                .flatMap(List::stream)
                .toList();

        final var retrievedIds = disciplineGateway.existsByIds(disciplines);
        if(disciplines.size() != retrievedIds.size()){
            final var missingDiscipines = new ArrayList<>(disciplines);
            missingDiscipines.removeAll(retrievedIds);
            final var message = missingDiscipines.stream().map(DisciplineID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(Error.withMessage("Some disciplines could not be found: %s".formatted(message)));

        }

        return notification;
    }

    private List<List<DisciplineID>> toSemesters(List<List<String>> semesters) {
        return semesters.stream()
                .map(semester -> semester.stream()
                        .map(DisciplineID::from)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}

package com.derblaz.educational.institution.api.application.course.create;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateCourseUseCase implements UseCase<CreateCourseCommand, CreateCourseOutput> {

    private final CourseGateway courseGateway;
    private final DisciplineGateway disciplineGateway;

    public CreateCourseUseCase(CourseGateway courseGateway, DisciplineGateway disciplineGateway) {
        this.courseGateway = Objects.requireNonNull(courseGateway);
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public CreateCourseOutput execute(CreateCourseCommand aCommand) {
        final var semestersIds = toSemesters(aCommand.semesters());

        final var disciplineIDS = semestersIds.stream()
                .flatMap(List::stream)
                .toList();

        final var disciplines = disciplineGateway.existsByIds(disciplineIDS);

        final var notification = Notification.create();
        if(disciplineIDS.size() != disciplines.size()){
            final var missingDiscipines = new ArrayList<>(disciplineIDS);
            missingDiscipines.removeAll(disciplines.stream().map(Discipline::getId).toList());
            final var message = missingDiscipines.stream().map(DisciplineID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(Error.withMessage("Some disciplines could not be found: %s".formatted(message)));
        }

        final var course = notification.validate(() ->
                        Course.newCourse(
                                aCommand.name(),
                                aCommand.description(),
                                aCommand.monthsDuration(),
                                aCommand.places()
                        )
                );

        if(notification.hasError()){
            throw new NotificationException("Cloud not create Course", notification);
        }


        course.addSemesters(toSemestersDisciplines(semestersIds, disciplines));
        return CreateCourseOutput.from(courseGateway.create(course));
    }

    private List<List<Discipline>> toSemestersDisciplines(List<List<DisciplineID>> semesters, List<Discipline> disciplines) {
        final var semestersDiscipline = new ArrayList<List<Discipline>>();

        for (int i = 0; i < semesters.size(); i++) {
            final var semesterIndex = i;
                final var disciplineFound = disciplines.stream()
                        .filter(discipline -> semesters.get(semesterIndex).contains(discipline.getId()))
                        .toList();
                semestersDiscipline.add(disciplineFound);
        }

        return  semestersDiscipline;
    }

    private List<List<DisciplineID>> toSemesters(List<List<String>> semesters) {
        return semesters.stream()
                .map(semester -> semester.stream()
                        .map(DisciplineID::from)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}

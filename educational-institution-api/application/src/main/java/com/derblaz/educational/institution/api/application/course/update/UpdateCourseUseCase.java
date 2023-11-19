package com.derblaz.educational.institution.api.application.course.update;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.discipline.Discipline;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UpdateCourseUseCase implements UseCase<UpdateCourseCommand, UpdateCourseOutput> {

    private final CourseGateway courseGateway;
    private final DisciplineGateway disciplineGateway;

    public UpdateCourseUseCase(CourseGateway courseGateway, DisciplineGateway disciplineGateway) {
        this.courseGateway = Objects.requireNonNull(courseGateway);
        this.disciplineGateway = Objects.requireNonNull(disciplineGateway);
    }

    @Override
    public UpdateCourseOutput execute(UpdateCourseCommand aCommand) {
        final var courseID = CourseID.from(aCommand.id());
        final var semestersIds = toSemesters(aCommand.semesters());

        var aCourse = courseGateway.findById(courseID)
                .orElseThrow(() -> NotFoundException.with(Course.class, courseID));

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
        final var course = notification.validate(() -> aCourse.update(
                aCommand.name(),
                aCommand.description(),
                aCommand.monthsDuration(),
                aCommand.places(),
                aCommand.active(),
                toSemestersDisciplines(semestersIds, disciplines)
        ));

        if(notification.hasError())
            throw new NotificationException("Could not update Course", notification);

        return UpdateCourseOutput.from(courseGateway.update(course));
    }

    private List<List<Discipline>> toSemestersDisciplines(List<List<DisciplineID>> semesters, List<Discipline> disciplines) {
        final var semestersDiscipline = new ArrayList<List<Discipline>>();

        for (int i = 0; i < semesters.size(); i++) {
            final var semesterIndex = i;
            final var disciplineFinded = disciplines.stream()
                    .filter(discipline -> semesters.get(semesterIndex).contains(discipline.getId()))
                    .toList();
            semestersDiscipline.add(disciplineFinded);
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

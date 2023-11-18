package com.derblaz.educational.institution.api.application.course.update;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.course.Course;
import com.derblaz.educational.institution.api.domain.course.CourseGateway;
import com.derblaz.educational.institution.api.domain.course.CourseID;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineGateway;
import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
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
        final var semesters = toSemesters(aCommand.semesters());

        var aCourse = courseGateway.findById(courseID)
                .orElseThrow(() -> NotFoundException.with(Course.class, courseID));
        
        
        final var notification = Notification.create();
        notification.append(validateDisciplines(semesters));
        final var course = notification.validate(() -> aCourse.update(
                aCommand.name(),
                aCommand.description(),
                aCommand.monthsDuration(),
                aCommand.places(),
                aCommand.active(),
                semesters
        ));

        if(notification.hasError())
            throw new NotificationException("Could not update Course", notification);

        return UpdateCourseOutput.from(course);
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

package com.derblaz.educational.institution.api.domain.course;

import com.derblaz.educational.institution.api.domain.discipline.DisciplineID;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    public void givenAValidEmptySemestersCourse_whenCallsAddSemesters_thenReceiveOk(){
        final var expectedSemester1 = List.of(DisciplineID.unique(), DisciplineID.unique());
        final var expectedSemester2 = List.of(DisciplineID.unique(), DisciplineID.unique());
        final var expectedSemesters = List.of(
                expectedSemester1,
                expectedSemester2
        );
        final var expectedSemestersSize = 2;

        final var actualCourse = Course.newCourse(
                "Course",
                "",
                30,
                50
        );

        assertEquals(0, actualCourse.getSemesters().size());


        actualCourse.addSemesters(expectedSemesters);


        assertEquals(expectedSemestersSize, actualCourse.getSemesters().size());
        assertEquals(expectedSemester1, actualCourse.getSemesters().get(0));
        assertEquals(expectedSemester2, actualCourse.getSemesters().get(1));

    }

    @Test
    public void givenAValidNotEmptySemestersCourse_whenCallsAddSemesters_thenReceiveOk(){
        final var discipline1 = DisciplineID.unique();
        final var discipline2 = DisciplineID.unique();
        final var discipline3 = DisciplineID.unique();
        final var discipline4 = DisciplineID.unique();
        final var discipline5 = DisciplineID.unique();
        final var discipline6 = DisciplineID.unique();
        final var discipline7 = DisciplineID.unique();
        final var discipline8 = DisciplineID.unique();

        final var semester1 = List.of(discipline1, discipline2);
        final var semester2 = List.of(discipline5, discipline6);
        final var semester3 = List.of(discipline3, discipline4);
        final var semester4 = List.of(discipline7, discipline8);
        final var semesters1 = List.of(
                semester1,
                semester2
        );
        final var semesters2 = List.of(
                semester3,
                semester4
        );
        final var expectedSemester1 = List.of(discipline1, discipline2, discipline3, discipline4);
        final var expectedSemester2 = List.of(discipline5, discipline6, discipline7, discipline8);
        final var expectedSemesters = List.of(
                expectedSemester1,
                expectedSemester2
        );
        final var expectedSemestersSize = 2;

        final var actualCourse = Course.newCourse(
                "Course",
                "",
                30,
                50
        );

        assertEquals(0, actualCourse.getSemesters().size());


        actualCourse.addSemesters(semesters1);
        actualCourse.addSemesters(semesters2);


        assertEquals(expectedSemestersSize, actualCourse.getSemesters().size());
        assertEquals(expectedSemester1, actualCourse.getSemesters().get(0));
        assertEquals(expectedSemester2, actualCourse.getSemesters().get(1));
        assertEquals(expectedSemesters, actualCourse.getSemesters());

    }

}
package com.derblaz.educational.institution.api.domain.course;

import com.derblaz.educational.institution.api.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CourseID extends Identifier {

    private final String value;

    private CourseID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CourseID unique(){
        return CourseID.from(UUID.randomUUID());
    }

    public static CourseID from(final String anId){
        return CourseID.from(UUID.fromString(anId));
    }

    public static CourseID from(final UUID anId){
        return new CourseID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CourseID that = (CourseID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

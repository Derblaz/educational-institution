package com.derblaz.educational.institution.api.domain.discipline;

import com.derblaz.educational.institution.api.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class DisciplineID extends Identifier {

    private final String value;

    private DisciplineID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static DisciplineID unique(){
        return DisciplineID.from(UUID.randomUUID());
    }

    public static DisciplineID from(final String anId){
        return DisciplineID.from(UUID.fromString(anId));
    }

    public static DisciplineID from(final UUID anId){
        return new DisciplineID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DisciplineID that = (DisciplineID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

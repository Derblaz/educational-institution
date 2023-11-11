package com.derblaz.educational.institution.api.domain.user;

import com.derblaz.educational.institution.api.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class UserID extends Identifier {

    private final String value;

    private UserID(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static UserID unique(){
        return UserID.from(UUID.randomUUID());
    }

    public static UserID from(final String anId){
        return UserID.from(UUID.fromString(anId));
    }

    public static UserID from(final UUID anId){
        return new UserID(anId.toString().toLowerCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserID that = (UserID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

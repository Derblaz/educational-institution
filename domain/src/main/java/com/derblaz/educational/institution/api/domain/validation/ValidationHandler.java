package com.derblaz.educational.institution.api.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler anHandler);
    default boolean hasError(){
        return getErrors() != null && !getErrors().isEmpty();
    }
    List<Error> getErrors();

}

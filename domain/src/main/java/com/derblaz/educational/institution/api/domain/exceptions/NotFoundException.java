package com.derblaz.educational.institution.api.domain.exceptions;

import com.derblaz.educational.institution.api.domain.AggregateRoot;
import com.derblaz.educational.institution.api.domain.Identifier;
import com.derblaz.educational.institution.api.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<Error> errors) {
        super(aMessage, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot> anAggregate,
            final Identifier id
    ){
        final var anErrorMessage = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anErrorMessage, Collections.emptyList());
    }
}

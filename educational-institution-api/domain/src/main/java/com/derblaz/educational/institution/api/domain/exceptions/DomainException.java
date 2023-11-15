package com.derblaz.educational.institution.api.domain.exceptions;

import com.derblaz.educational.institution.api.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    protected final List<Error> errors;

    protected DomainException(final String aMessage, final List<Error> errors) {
        super(aMessage);
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}

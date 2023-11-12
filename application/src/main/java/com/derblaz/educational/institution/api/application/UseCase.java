package com.derblaz.educational.institution.api.application;

public interface UseCase<IN, OUT> {
    OUT execute(IN aCommand);
}
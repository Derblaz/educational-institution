package com.derblaz.educational.institution.api.application;

public interface UnitUseCase<IN> {
    void execute(IN aCommand);
}

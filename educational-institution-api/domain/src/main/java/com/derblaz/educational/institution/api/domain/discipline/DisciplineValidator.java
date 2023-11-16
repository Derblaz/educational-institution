package com.derblaz.educational.institution.api.domain.discipline;

import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.Validator;

public class DisciplineValidator extends Validator {

    private final Discipline discipline;
    public DisciplineValidator(Discipline discipline, ValidationHandler handler) {
        super(handler);
        this.discipline = discipline;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkCreditsConstrants();
    }

    private void checkNameConstraints() {
        String name = this.discipline.getName();
        if(name == null){
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if(length < 5 || length > 100){
            this.validationHandler().append(new Error("'name' must be between %s and %s characters".formatted(5, 100)));
        }
    }

    private void checkCreditsConstrants(){
        if(this.discipline.getCredits() < 0)
            this.validationHandler().append(Error.withMessage("'credits' must be greater than 0"));
    }
}

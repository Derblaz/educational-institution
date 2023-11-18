package com.derblaz.educational.institution.api.domain.course;

import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.Validator;

public class CourseValidator extends Validator {
    
    private final Course course;
    
    public CourseValidator(Course course, ValidationHandler handler) {
        super(handler);
        this.course = course;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        CheckMonthsDurationConstraints();
        CheckPlacesConstraints();
    }

    private void checkNameConstraints() {
        String name = this.course.getName();
        if(name == null){
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if( length < 3 || length  > 100 ) {
            this.validationHandler().append(new Error("'name' must be between 3 and 100 characters"));
        }
    }

    private void CheckMonthsDurationConstraints() {
        if(this.course.getMonthsDuration() < 0){
            this.validationHandler().append(new Error("'monthsDuration' must be greater zero"));
        }
    }

    private void CheckPlacesConstraints() {
        if(this.course.getPlaces() < 0){
            this.validationHandler().append(new Error("'monthsDuration' must be greater zero"));
        }
    }

}

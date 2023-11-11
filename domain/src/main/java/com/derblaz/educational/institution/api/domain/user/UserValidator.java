package com.derblaz.educational.institution.api.domain.user;

import com.derblaz.educational.institution.api.domain.validation.Error;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.Validator;

public class UserValidator extends Validator {

    public static final int NAME_USERNAME_MIN_LENGTH = 3;
    public static final int NAME_USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 8;
    private final User user;

    protected UserValidator(User user, ValidationHandler handler) {
        super(handler);
        this.user = user;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkProfileConstraints();
        checkUsernameConstraints();
        checkPasswordConstraints();
    }

    private void checkNameConstraints() {
        String name = this.user.getName();
        if(name == null){
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if(length > NAME_USERNAME_MAX_LENGTH || length < NAME_USERNAME_MIN_LENGTH){
            this.validationHandler().append(new Error("'name' must be between %s and %s characters".formatted(NAME_USERNAME_MIN_LENGTH, NAME_USERNAME_MAX_LENGTH)));
        }
    }

    private void checkProfileConstraints() {
        if(this.user.getProfile() == null){
            this.validationHandler().append(Error.withMessage("'profile' should not be null"));
        }
    }

    private void checkUsernameConstraints() {
        final var username = this.user.getUsername();
        if(username == null){
            this.validationHandler().append(new Error("'username' should not be null"));
            return;
        }

        if(username.isBlank()){
            this.validationHandler().append(new Error("'username' should not be empty"));
            return;
        }

        final int length = username.trim().length();
        if(length > NAME_USERNAME_MAX_LENGTH || length < NAME_USERNAME_MIN_LENGTH){
            this.validationHandler().append(new Error("'username' must be between %s and %s characters".formatted(NAME_USERNAME_MIN_LENGTH, NAME_USERNAME_MAX_LENGTH)));
        }
    }

    private void checkPasswordConstraints() {
        final var password = this.user.getPassword();
        if(password == null){
            this.validationHandler().append(new Error("'password' should not be null"));
            return;
        }

        if(password.isBlank()){
            this.validationHandler().append(new Error("'password' should not be empty"));
            return;
        }

        final int length = password.trim().length();
        if(length < PASSWORD_MIN_LENGTH || length > PASSWORD_MAX_LENGTH){
            this.validationHandler().append(new Error("'password' must be between %s and %s characters".formatted(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH)));
        }
    }

}

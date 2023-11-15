package com.derblaz.educational.institution.api.domain.validation;

public record Error(String message) {

    public static Error withMessage(String message){
        return new Error(message);
    }
}

package com.derblaz.educational.institution.api.application.user.create;

import com.derblaz.educational.institution.api.domain.user.User;

public record CreateUserOutput(String id) {

    public static CreateUserOutput from(User user){
        return new CreateUserOutput(user.getId().getValue());
    }


}

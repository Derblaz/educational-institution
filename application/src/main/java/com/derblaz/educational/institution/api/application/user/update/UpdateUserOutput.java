package com.derblaz.educational.institution.api.application.user.update;

import com.derblaz.educational.institution.api.domain.user.User;

public record UpdateUserOutput(String id) {

    public static UpdateUserOutput from(User user){
        return new UpdateUserOutput(user.getId().getValue());
    }
}

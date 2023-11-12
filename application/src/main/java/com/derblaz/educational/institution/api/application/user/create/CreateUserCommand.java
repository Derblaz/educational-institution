package com.derblaz.educational.institution.api.application.user.create;

import com.derblaz.educational.institution.api.domain.user.Profile;

public record CreateUserCommand(String name, Profile profile, String username, String password) {
    public static CreateUserCommand with(String name, Profile profile, String username, String password) {
        return new CreateUserCommand(name, profile, username, password);
    }
}

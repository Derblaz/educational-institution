package com.derblaz.educational.institution.api.application.user.update;

import com.derblaz.educational.institution.api.domain.user.Profile;

public record UpdateUserCommand(String id,
                                String name,
                                Profile profile,
                                String username,
                                String password,
                                boolean isActive) {
    public static UpdateUserCommand with(String id,
                                         String name,
                                         Profile profile,
                                         String username,
                                         String password,
                                         boolean isActive
    ) {
        return new UpdateUserCommand(id, name, profile, username, password, isActive);
    }
}

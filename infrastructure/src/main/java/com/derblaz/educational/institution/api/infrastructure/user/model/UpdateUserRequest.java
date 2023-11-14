package com.derblaz.educational.institution.api.infrastructure.user.model;

import com.derblaz.educational.institution.api.domain.user.Profile;

public record UpdateUserRequest(
        String name,
        Profile profile,
        String username,
        String password,
        Boolean isActive
) {
}

package com.derblaz.educational.institution.api.application.user.retrieve.get;

import com.derblaz.educational.institution.api.domain.user.Profile;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserID;

import java.time.Instant;

public record UserOutput(
        UserID id,
        String name,
        Profile profile,
        String username,
        String password,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static UserOutput from(User user){
        return new UserOutput(
                user.getId(),
                user.getName(),
                user.getProfile(),
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }
}

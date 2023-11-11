package com.derblaz.educational.institution.api.domain.user;

import com.derblaz.educational.institution.api.domain.AggregateRoot;

import java.time.Instant;
import java.util.Objects;

public class User extends AggregateRoot<UserID> {
    private String name;
    private Profile profile;
    private String username;
    private String password;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private User(
            UserID userID,
            String name,
            Profile profile,
            String username,
            String password,
            boolean active,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        super(userID);
        this.name = name;
        this.profile = profile;
        this.username = username;
        this.password = password;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.deletedAt = deletedAt;
    }

    public static User newUser(
            final String name,
            final Profile profile,
            final String username,
            final String password
    ){
        return new User(
                UserID.unique(),
                name,
                profile,
                username,
                password,
                true,
                Instant.now(),
                Instant.now(),
                null

        );
    }

    public String getName() {
        return name;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}

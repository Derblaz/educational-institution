package com.derblaz.educational.institution.api.domain.user;

import com.derblaz.educational.institution.api.domain.AggregateRoot;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.validation.ValidationHandler;
import com.derblaz.educational.institution.api.domain.validation.handler.Notification;

import java.time.Instant;

public class User extends AggregateRoot<User, UserID> {
    private String name;
    private Profile profile;
    private String username;
    private String password;

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
        super(userID, active, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.profile = profile;
        this.username = username;
        this.password = password;


        this.selfValidate();
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

    public static User with(String id, String name, Profile profile, String username, String password, boolean active, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        return new User(
                UserID.from(id),
                name,
                profile,
                username,
                password,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    private void selfValidate() {
        final var notification = Notification.create();
        this.validate(notification);

        if(notification.hasError()){
            throw new NotificationException("", notification);
        }

    }

    private void validate(ValidationHandler notification) {
        new UserValidator(this, notification).validate();
    }


    public User update(
            final String name,
            final Profile profile,
            final String username,
            final String password,
            final boolean isActive
    ){
        this.name = name;
        this.profile = profile;
        this.username = username;
        this.password = password;
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        selfValidate();
        return this;
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

}

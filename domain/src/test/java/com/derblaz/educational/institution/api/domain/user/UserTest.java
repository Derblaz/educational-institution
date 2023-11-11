package com.derblaz.educational.institution.api.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void givenAValidParam_whenCallsNewUser_thenInstantiateAnActiveUser(){
        final var expectedName = "User Admin";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "useradmin";
        final var expectedPassword = "123456";

        final var actualUser = User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );

        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertTrue(actualUser.isActive());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertNull(actualUser.getDeletedAt());
    }

}
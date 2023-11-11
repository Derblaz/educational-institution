package com.derblaz.educational.institution.api.domain.user;

import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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


    @ParameterizedTest
    @CsvSource({
            ",ADMIN, username, password,'name' should not be null",
            "'   ',ADMIN, username, password,'name' should not be empty",
            "aa,ADMIN, username, password,'name' must be between 3 and 50 characters",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,ADMIN, username, password,'name' must be between 3 and 50 characters",
            "User,,username,password,'profile' should not be null",
            "User,ADMIN,,password,'username' should not be null",
            "User,ADMIN,'  ',password,'username' should not be empty",
            "User,ADMIN,aa,password,'username' must be between 3 and 50 characters",
            "User,ADMIN,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,password,'username' must be between 3 and 50 characters",
            "User,ADMIN,username,,'password' should not be null",
            "User,ADMIN,username,'  ','password' should not be empty",
            "User,ADMIN,username,aaa,'password' must be between 4 and 8 characters",
            "User,ADMIN,username,aaaaaaaaa,'password' must be between 4 and 8 characters",
    })
    public void givenAnInvalidParam_whenCallsNewUser_shouldReceiveError(
        final String expectedName,
        final String expectedProfile,
        final String expectedUsername,
        final String expectedPassword,
        final String expectedErrorMessage
    ){

        final var actualException = assertThrows(NotificationException.class, () -> User.newUser(
                expectedName,
                expectedProfile != null ? Profile.valueOf(expectedProfile) : null,
                expectedUsername,
                expectedPassword
        ));

        assertEquals(1, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).withMessage());
    }

    @Test
    public void givenMoreThanOneInvalidParam_whenCallsNewUser_shouldReceiveErrors(){
        final var expectedName = "";
        final var expectedProfile = Profile.ADMIN;
        final String expectedUsername = null ;
        final var expectedPassword = "password";
        final var expectedErrorMessageOne = "'name' should not be empty";
        final var expectedErrorMessageTwo = "'username' should not be null";
        final var expectedErrorSize = 2;


        final var actualException = assertThrows(NotificationException.class, () -> User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        ));

        assertEquals(expectedErrorSize, actualException.getErrors().size());
        assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).withMessage());
        assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).withMessage());
    }
}
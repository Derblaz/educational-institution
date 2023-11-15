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
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
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
        assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());
    }


    @Test
    public void givenAValidActiveUser_whenCallsDeactivate_shouldReturnInactiveUser() throws InterruptedException {
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final String expectedUsername = "username" ;
        final var expectedPassword = "password";

        final var actualUser = User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );
        final var createAtBeforeDeactivate = actualUser.getCreatedAt();
        final var updateAtBeforeDeactivate = actualUser.getUpdatedAt();

        assertTrue(actualUser.isActive());
        assertNull(actualUser.getDeletedAt());


        Thread.sleep(10);
        actualUser.deactivate();


        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertFalse(actualUser.isActive());
        assertEquals(createAtBeforeDeactivate, actualUser.getCreatedAt());
        assertTrue(updateAtBeforeDeactivate.isBefore(actualUser.getUpdatedAt()));
        assertNotNull(actualUser.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveUser_whenCallsActivate_shouldReturnActiveUser() throws InterruptedException {
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final String expectedUsername = "username";
        final var expectedPassword = "password";

        final var actualUser = User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        ).deactivate();

        final var createAtBeforeDeactivate = actualUser.getCreatedAt();
        final var updateAtBeforeDeactivate = actualUser.getUpdatedAt();

        assertFalse(actualUser.isActive());
        assertNotNull(actualUser.getDeletedAt());


        Thread.sleep(10);
        actualUser.activate();


        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertTrue(actualUser.isActive());
        assertEquals(createAtBeforeDeactivate, actualUser.getCreatedAt());
        assertTrue(updateAtBeforeDeactivate.isBefore(actualUser.getUpdatedAt()));
        assertNull(actualUser.getDeletedAt());
    }

    @Test
    public void givenAValidParam_whenCallsUpdateUser_shouldReturnUpdatedUser() throws InterruptedException {
        final var actualUser = User.newUser(
                "User",
                Profile.ADMIN,
                "username",
                "password"
        );

        final var createAtBeforeUpdate = actualUser.getCreatedAt();
        final var updateAtBeforeUpdate = actualUser.getUpdatedAt();
        final var expectedName = "updatedUser";
        final var expectedProfile = Profile.COORDINATOR;
        final String expectedUsername = "updatedUsername";
        final var expectedPassword = "updated";
        final var expectedActivation = false;


        Thread.sleep(10);
        actualUser.update(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );


        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertEquals(expectedActivation, actualUser.isActive());
        assertEquals(createAtBeforeUpdate, actualUser.getCreatedAt());
        assertTrue(updateAtBeforeUpdate.isBefore(actualUser.getUpdatedAt()));
        assertNotNull(actualUser.getDeletedAt());
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
    public void givenAnInvalidParam_whenCallsUpdateUser_shouldReturnAnError(
        final String expectedName,
        final String expectedProfile,
        final String expectedUsername,
        final String expectedPassword,
        final String expectedErrorMessage
    ) {
        final var actualUser = User.newUser(
                "oldUser",
                Profile.COORDINATOR,
                "oldUsername",
                "1234"
        );
        final var expectedActivation = true;
        final var expectedErrorSize = 1;


        final var actualException = assertThrows(NotificationException.class,  () -> actualUser.update(
                expectedName,
                expectedProfile != null ? Profile.valueOf(expectedProfile) : null,
                expectedUsername,
                expectedPassword,
                expectedActivation
        ));


        assertEquals(expectedErrorSize, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInactiveUser_whenCallsUpdateUserWithActivationTrue_thenReturnActiveUser() throws InterruptedException {
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final String expectedUsername = "username";
        final var expectedPassword = "password";
        final var expectedActivation = true;

        final var actualUser = User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        ).deactivate();
        final var createAtBeforeUpdate = actualUser.getCreatedAt();
        final var updateAtBeforeUpdate = actualUser.getUpdatedAt();


        Thread.sleep(10);
        actualUser.update(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );


        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertTrue(actualUser.isActive());
        assertEquals(createAtBeforeUpdate, actualUser.getCreatedAt());
        assertTrue(updateAtBeforeUpdate.isBefore(actualUser.getUpdatedAt()));
        assertNull(actualUser.getDeletedAt());
    }

    @Test
    public void givenAnActiveUser_whenCallsUpdateUserWithActivationFalse_thenReturnInactiveUser() throws InterruptedException {
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final String expectedUsername = "username";
        final var expectedPassword = "password";
        final var expectedActivation = false;

        final var actualUser = User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );
        final var createAtBeforeUpdate = actualUser.getCreatedAt();
        final var updateAtBeforeUpdate = actualUser.getUpdatedAt();
        assertTrue(actualUser.isActive());
        assertNull(actualUser.getDeletedAt());

        Thread.sleep(10);
        actualUser.update(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );


        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertFalse(actualUser.isActive());
        assertEquals(createAtBeforeUpdate, actualUser.getCreatedAt());
        assertTrue(updateAtBeforeUpdate.isBefore(actualUser.getUpdatedAt()));
        assertNotNull(actualUser.getDeletedAt());
    }

}
package com.derblaz.educational.institution.api.application.user.update;

import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.user.Profile;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @InjectMocks
    UpdateUserUseCase useCase;

    @Mock
    UserGateway userGateway;

    @BeforeEach
    public void setup(){
        reset(userGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUser_thenReturnUserId(){
        final var aUser = User.newUser(
                "user",
                Profile.COORDINATOR,
                "username",
                "1234"
        );
        final var expectedName = "User Admin";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "useradmin";
        final var expectedPassword = "123456";
        final var expectedActivation = true;
        
        when(userGateway.findById(any()))
                .thenReturn(Optional.of(aUser));

        when(userGateway.update(any()))
                .thenAnswer(returnsFirstArg());
        
        final var command = UpdateUserCommand.with(
                aUser.getId().getValue(),
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );



        final var output = useCase.execute(command);



        assertNotNull(output);
        assertNotNull(output.id());

        verify(userGateway, times(1)).findById(eq(aUser.getId()));

        final var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userGateway, times(1)).update(userArgumentCaptor.capture());

        final var actualUser = userArgumentCaptor.getValue();
        assertNotNull(actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertTrue(actualUser.isActive());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertNull(actualUser.getDeletedAt());
    }

    @Test
    public void givenAValidInativateCommand_whenCallsUpdateUser_thenReturnUpdatedInactiveUser(){
        final var aUser = User.newUser(
                "user",
                Profile.COORDINATOR,
                "username",
                "1234"
        );
        final var expectedName = "User Admin";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "useradmin";
        final var expectedPassword = "123456";
        final var expectedActivation = false;

        when(userGateway.findById(any()))
                .thenReturn(Optional.of(aUser));

        when(userGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var command = UpdateUserCommand.with(
                aUser.getId().getValue(),
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );



        final var output = useCase.execute(command);



        assertNotNull(output);
        assertNotNull(output.id());

        verify(userGateway, times(1)).findById(eq(aUser.getId()));

        final var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userGateway, times(1)).update(userArgumentCaptor.capture());

        final var actualUser = userArgumentCaptor.getValue();
        assertNotNull(actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertFalse(actualUser.isActive());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertNotNull(actualUser.getDeletedAt());
    }

    @Test
    public void givenAInvalidParamCommnad_whenCallsUpdateUser_thenReturnAException(){
        final var aUser = User.newUser(
                "user",
                Profile.COORDINATOR,
                "username",
                "1234"
        );

        when(userGateway.findById(any()))
                .thenReturn(Optional.of(aUser));

        final var expectedName = "";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "useradmin";
        final var expectedPassword = "123456";
        final var expectedActivation = true;
        final var expectedErrorSize = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var command = UpdateUserCommand.with(
                aUser.getId().getValue(),
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );



        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(command));



        assertEquals(expectedErrorSize, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        verify(userGateway, times(1)).findById(eq(aUser.getId()));
        verify(userGateway, times(0)).update(any());

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRadomException_thenReturnAException(){
        final var aUser = User.newUser(
                "user",
                Profile.COORDINATOR,
                "username",
                "1234"
        );

        when(userGateway.findById(any()))
                .thenReturn(Optional.of(aUser));

        final var expectedId = UserID.unique();
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "username";
        final var expectedPassword = "password";
        final var expectedActivation = true;
        final var expectedErrorMessage = "Gateway error";

        final var command = UpdateUserCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );

        when(userGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));


        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals(1, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        final var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userGateway, times(1)).update(userArgumentCaptor.capture());

        final var actualUser = userArgumentCaptor.getValue();
        assertNotNull(actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedProfile, actualUser.getProfile());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedPassword, actualUser.getPassword());
        assertTrue(actualUser.isActive());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertNull(actualUser.getDeletedAt());
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdate_thenReturnNotFoundException(){
        final var expectedId = UserID.unique();
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "username";
        final var expectedPassword = "password";
        final var expectedActivation = true;
        final var expectedErrorMessage = "User with ID %s was not found".formatted(expectedId.getValue());

        final var command = UpdateUserCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword,
                expectedActivation
        );


        final var actualException = assertThrows(NotFoundException.class, () -> useCase.execute(command));


        assertEquals(0, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
package com.derblaz.educational.institution.api.application.user.create;

import com.derblaz.educational.institution.api.domain.exceptions.NotificationException;
import com.derblaz.educational.institution.api.domain.user.Profile;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @InjectMocks
    CreateUserUseCase useCase;

    @Mock
    UserGateway userGateway;

    @BeforeEach
    public void setup(){
        reset(userGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateUser_thenReturnUserId(){
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "username";
        final var expectedPassword = "password";
        
        final var command = CreateUserCommand.with(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );

        when(userGateway.create(any()))
                .thenAnswer(returnsFirstArg());


        final var output = useCase.execute(command);

        assertNotNull(output);
        assertNotNull(output.id());

        final var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userGateway, times(1)).create(userArgumentCaptor.capture());

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
    public void givenACommandWithInvalidParam_whenCallsCreateUser_thenReturnNotificationException(){
        final var expectedName = "";
        final Profile expectedProfile = null;
        final var expectedUsername = "username";
        final var expectedPassword = "password";
        final var expectedErrorMessageOne = "'name' should not be empty";
        final var expectedErrorMessageTwo = "'profile' should not be null";
        final var expectedErrorSize = 2;

        final var command = CreateUserCommand.with(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );
        
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals(expectedErrorSize, actualException.getErrors().size());
        assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        verify(userGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException(){
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "username";
        final var expectedPassword = "password";
        final var expectedErrorMessage = "Gateway error";

        final var command = CreateUserCommand.with(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );

        when(userGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        
        final var actualException = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals(1, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        final var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userGateway, times(1)).create(userArgumentCaptor.capture());

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

}
package com.derblaz.educational.institution.api.application.user.retrieve.get;

import com.derblaz.educational.institution.api.domain.exceptions.NotFoundException;
import com.derblaz.educational.institution.api.domain.user.Profile;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserByIdUseCaseTest {

    @InjectMocks
    GetUserByIdUseCase useCase;

    @Mock
    UserGateway userGateway;

    @BeforeEach
    public void setup(){
        reset(userGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetUser_shouldReturnUser() {
        final var expectedName = "User";
        final var expectedProfile = Profile.ADMIN;
        final var expectedUsername = "username";
        final var expectedPassword = "password";
        final var aUser = User.newUser(
                expectedName,
                expectedProfile,
                expectedUsername,
                expectedPassword
        );
        final var expectedId = aUser.getId();
        when(userGateway.findById(any()))
                .thenReturn(Optional.of(aUser));


        final var output = useCase.execute(expectedId.getValue());


        assertEquals(expectedId, output.id());
        assertEquals(expectedName, output.name());
        assertEquals(expectedProfile, output.profile());
        assertEquals(expectedUsername, output.username());
        assertEquals(expectedPassword, output.password());
        assertTrue(output.isActive());
        assertNotNull(output.createdAt());
        assertNotNull(output.updatedAt());
        assertNull(output.deletedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsGetUser_shouldReturnNotFound() {
        final var expectedId = UserID.unique();
        final var expectedErrorSize = 0;
        final var expectedErrorMessage = "User with ID %s was not found".formatted(expectedId.getValue());

        final var actualException = assertThrows(NotFoundException.class, () -> useCase.execute(expectedId.getValue()));

        assertEquals(expectedErrorSize, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = UserID.unique();

        when(userGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

}
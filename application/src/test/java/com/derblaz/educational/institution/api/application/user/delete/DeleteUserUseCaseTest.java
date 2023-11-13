package com.derblaz.educational.institution.api.application.user.delete;

import com.derblaz.educational.institution.api.domain.user.UserGateway;
import com.derblaz.educational.institution.api.domain.user.UserID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @InjectMocks
    DeleteUserUseCase useCase;

    @Mock
    UserGateway userGateway;

    @BeforeEach
    public void setup(){
        reset(userGateway);
    }

    @Test
    public void givenAValidOrNotExistId_whenCallsDeteleUser_thenShouldBeOk(){
        final var expectedId = UserID.unique();

        doNothing()
                .when(userGateway).deleteById(any());

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        verify(userGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsDeteleUser_thenReturnException(){
        final var expectedId = "123";

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(expectedId));

        verify(userGateway, never()).deleteById(any());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenReturnException(){
        final var expectedId = UserID.unique();

        doThrow(new IllegalStateException("Gateway error"))
                .when(userGateway).deleteById(any());

        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        verify(userGateway).deleteById(eq(expectedId));
    }
}
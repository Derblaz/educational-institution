package com.derblaz.educational.institution.api.application.user.retrieve.list;

import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.domain.user.Profile;
import com.derblaz.educational.institution.api.domain.user.User;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListUserUseCaseTest {

    @InjectMocks
    ListUserUseCase useCase;

    @Mock
    UserGateway userGateway;

    @BeforeEach
    public void setup(){
        reset(userGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListUser_shouldReturnUsers(){
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createAt";
        final var expectedDirection = "asc";
        final var query = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );
        final var users = List.of(
                User.newUser("name1", Profile.ADMIN, "username1", "pass1"),
                User.newUser("name2", Profile.COORDINATOR, "username2", "pass2")
        );
        final var expectedItensCount = users.size();
        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, users.size(), users);
        final var expectedResult = expectedPagination.map(UserListOutput::from);

        when(userGateway.findAll(eq(query)))
                .thenReturn(expectedPagination);


        final Pagination<UserListOutput> output = useCase.execute(query);


        assertEquals(expectedItensCount, output.items().size());
        assertEquals(expectedResult, output);
        assertEquals(expectedPage, output.currentPage());
        assertEquals(expectedPerPage, output.perPage());
        assertEquals(users.size(), output.total());
    }

    @Test
    public void givenAValidQuery_whenCallsListUser_shouldReturnEmptyUsers(){
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createAt";
        final var expectedDirection = "asc";
        final var query = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );
        final var users = Collections.<User>emptyList();
        final var expectedItensCount = users.size();
        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, users.size(), users);
        final var expectedResult = expectedPagination.map(UserListOutput::from);

        when(userGateway.findAll(eq(query)))
                .thenReturn(expectedPagination);


        final Pagination<UserListOutput> output = useCase.execute(query);


        assertEquals(expectedItensCount, output.items().size());
        assertEquals(expectedResult, output);
        assertEquals(expectedPage, output.currentPage());
        assertEquals(expectedPerPage, output.perPage());
        assertEquals(users.size(), output.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_shouldReturnException(){
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createAt";
        final var expectedDirection = "asc";
        final var expectedErrorMessage = "Gateway Error";

        final var query = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        when(userGateway.findAll(eq(query)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));


        final var actualException = assertThrows(IllegalStateException.class, () ->
                useCase.execute(query)
        );


        assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
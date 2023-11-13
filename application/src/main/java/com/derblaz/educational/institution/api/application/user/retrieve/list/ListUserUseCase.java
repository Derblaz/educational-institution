package com.derblaz.educational.institution.api.application.user.retrieve.list;

import com.derblaz.educational.institution.api.application.UseCase;
import com.derblaz.educational.institution.api.domain.pagination.Pagination;
import com.derblaz.educational.institution.api.domain.pagination.SearchQuery;
import com.derblaz.educational.institution.api.domain.user.UserGateway;

import java.util.Objects;

public class ListUserUseCase implements UseCase<SearchQuery, Pagination<UserListOutput>> {

    private final UserGateway userGateway;

    public ListUserUseCase(UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public Pagination<UserListOutput> execute(SearchQuery query) {
        return userGateway.findAll(query)
                .map(UserListOutput::from);
    }
}

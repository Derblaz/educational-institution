package com.derblaz.educational.institution.api.infrastructure.configuration.usecases;

import com.derblaz.educational.institution.api.application.user.create.CreateUserUseCase;
import com.derblaz.educational.institution.api.application.user.delete.DeleteUserUseCase;
import com.derblaz.educational.institution.api.application.user.retrieve.get.GetUserByIdUseCase;
import com.derblaz.educational.institution.api.application.user.retrieve.list.ListUserUseCase;
import com.derblaz.educational.institution.api.application.user.update.UpdateUserUseCase;
import com.derblaz.educational.institution.api.domain.user.UserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    private final UserGateway userGateway;

    public UserUseCaseConfig(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Bean
    public CreateUserUseCase createUserUseCase(){
        return new CreateUserUseCase(userGateway);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(){
        return new GetUserByIdUseCase(userGateway);
    }

    @Bean
    public ListUserUseCase listUserUseCase(){
        return new ListUserUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(){
        return new UpdateUserUseCase(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(){
        return new DeleteUserUseCase(userGateway);
    }
}

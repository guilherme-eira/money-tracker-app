package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.user.CreateUserInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserAlreadyExistsException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    CreateUserUseCase createUserUseCase;

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists(){
        var input = new CreateUserInput(
                "name",
                "document",
                "email",
                "password"
        );

        BDDMockito.given(repository.existsByDocumentOrEmail("document", "email")).willReturn(true);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            createUserUseCase.execute(input);
        });
    }

}
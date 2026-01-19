package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.UpdateGoalInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.GoalNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UpdateGoalUseCaseTest {

    @Mock
    GoalRepository repository;

    @InjectMocks
    UpdateGoalUseCase updateGoalUseCase;

    @Test
    void shouldThrowExceptionWhenGoalIsNotFound(){
        var goalId = UUID.randomUUID();
        var input = new UpdateGoalInput(
                goalId,
                BigDecimal.valueOf(1000)
        );

        BDDMockito.given(repository.findById(goalId)).willReturn(Optional.empty());

        Assertions.assertThrows(GoalNotFoundException.class, () -> {
            updateGoalUseCase.execute(input);
        });
    }
}
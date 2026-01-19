package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.CreateGoalInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.CategoryNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.exception.GoalAlreadyExistsException;
import io.github.guilherme_eira.money_tracker_app.application.exception.InvalidStartDateException;
import io.github.guilherme_eira.money_tracker_app.application.exception.UserNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateGoalUseCaseTest {

    @Mock
    GoalRepository goalRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CreateGoalUseCase createGoalUseCase;

    @Test
    void shouldThrowExceptionWhenDateIsNotValid(){
        var categoryId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var input = new CreateGoalInput(
                categoryId,
                BigDecimal.valueOf(1000),
                YearMonth.of(2025, 12),
                userId
        );

        Assertions.assertThrows(InvalidStartDateException.class, () -> {
            createGoalUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenGoalAlreadyExists(){
        var categoryId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var input = new CreateGoalInput(
                categoryId,
                BigDecimal.valueOf(1000),
                YearMonth.now(),
                userId
        );
        var startDate = LocalDate.of(input.startDate().getYear(), input.startDate().getMonthValue(), 1);

        BDDMockito.given(goalRepository.existsByUserIdAndCategoryIdAndStartDateBetween(userId,
                categoryId, startDate, startDate.withDayOfMonth(startDate.lengthOfMonth()))).willReturn(true);

        Assertions.assertThrows(GoalAlreadyExistsException.class, () -> {
            createGoalUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound(){
        var categoryId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var input = new CreateGoalInput(
                categoryId,
                BigDecimal.valueOf(1000),
                YearMonth.now(),
                userId
        );
        var startDate = LocalDate.of(input.startDate().getYear(), input.startDate().getMonthValue(), 1);

        BDDMockito.given(goalRepository.existsByUserIdAndCategoryIdAndStartDateBetween(userId,
                categoryId, startDate, startDate.withDayOfMonth(startDate.lengthOfMonth()))).willReturn(false);

        BDDMockito.given(userRepository.findById(userId)).willReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            createGoalUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenCategoryIsNotFound(){
        var user = new User();
        var userId = UUID.randomUUID();
        var categoryId = UUID.randomUUID();
        var input = new CreateGoalInput(
                categoryId,
                BigDecimal.valueOf(1000),
                YearMonth.now(),
                userId
        );
        var startDate = LocalDate.of(input.startDate().getYear(), input.startDate().getMonthValue(), 1);

        BDDMockito.given(goalRepository.existsByUserIdAndCategoryIdAndStartDateBetween(userId,
                categoryId, startDate, startDate.withDayOfMonth(startDate.lengthOfMonth()))).willReturn(false);

        BDDMockito.given(userRepository.findById(userId)).willReturn(Optional.of(user));
        BDDMockito.given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            createGoalUseCase.execute(input);
        });
    }

}
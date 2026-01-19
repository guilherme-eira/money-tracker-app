package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.CreateGoalInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.*;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.UserRepository;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import io.github.guilherme_eira.money_tracker_app.domain.model.Goal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateGoalUseCase{

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void execute(CreateGoalInput input) {

        if (input.startDate().isBefore(YearMonth.now())){
            throw new InvalidStartDateException();
        }

        var startDate = LocalDate.of(input.startDate().getYear(), input.startDate().getMonthValue(), 1);

        if (goalRepository.existsByUserIdAndCategoryIdAndStartDateBetween(input.userId(), input.categoryId(), startDate, startDate.withDayOfMonth(startDate.lengthOfMonth()))) {
            throw new GoalAlreadyExistsException();
        }

        var user = userRepository.findById(input.userId())
                .orElseThrow(UserNotFoundException::new);
        var category = categoryRepository.findById(input.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        if (category.getTransactionType() != TransactionType.EXPENSE){
            throw new InvalidTransactionTypeException();
        }

        var goal = new Goal(
                UUID.randomUUID(),
                input.maxExpense(),
                startDate,
                LocalDateTime.now(),
                null,
                user,
                category
        );

        goalRepository.save(goal);
    }
}

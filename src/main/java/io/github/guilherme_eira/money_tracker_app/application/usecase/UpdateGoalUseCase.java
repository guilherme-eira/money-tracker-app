package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.UpdateGoalInput;
import io.github.guilherme_eira.money_tracker_app.application.exception.GoalNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateGoalUseCase {

    private final GoalRepository repository;

    @Transactional
    public void execute(UpdateGoalInput input){
        var goal = repository.findById(input.goalId()).orElseThrow(GoalNotFoundException::new);
        goal.setMaxExpense(input.maxExpense());
        goal.setUpdatedAt(LocalDateTime.now());
        repository.save(goal);
    }
}

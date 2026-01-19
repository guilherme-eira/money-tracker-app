package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.exception.GoalNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteGoalUseCase {

    private final GoalRepository repository;

    @Transactional
    public void execute(UUID id){
        var goal = repository.findById(id).orElseThrow(GoalNotFoundException::new);
        repository.delete(goal);
    }
}

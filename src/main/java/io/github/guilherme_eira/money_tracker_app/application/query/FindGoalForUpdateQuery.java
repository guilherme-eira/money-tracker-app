package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.GoalDetailsOutput;
import io.github.guilherme_eira.money_tracker_app.application.exception.GoalNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.application.mapper.GoalOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindGoalForUpdateQuery {

    private final GoalRepository repository;
    private final GoalOutputMapper mapper;

    public GoalDetailsOutput execute(UUID id){
        var goal = repository.findById(id).orElseThrow(GoalNotFoundException::new);
        return mapper.toGoalDetailsOutput(goal);
    }
}

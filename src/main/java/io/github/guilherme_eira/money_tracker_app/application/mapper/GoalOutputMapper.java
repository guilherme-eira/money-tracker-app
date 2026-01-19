package io.github.guilherme_eira.money_tracker_app.application.mapper;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.GoalCardOutput;
import io.github.guilherme_eira.money_tracker_app.application.dto.goal.GoalDetailsOutput;
import io.github.guilherme_eira.money_tracker_app.application.dto.overview.GoalSummaryOutput;
import io.github.guilherme_eira.money_tracker_app.domain.model.Goal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
public class GoalOutputMapper {

    public Page<GoalCardOutput> toGoalCardOutput(Page<Goal> goals, Map<String, BigDecimal> totalExpensesByCategory) {
        return goals.map(goal -> {
            BigDecimal maxExpense = goal.getMaxExpense();
            BigDecimal currentExpense = totalExpensesByCategory.getOrDefault(goal.getCategory().getName(), BigDecimal.ZERO);

            return new GoalCardOutput(
                    goal.getId(),
                    goal.getCategory().getName(),
                    currentExpense,
                    maxExpense,
                    calculateProgress(currentExpense, maxExpense),
                    currentExpense.compareTo(maxExpense) > 0,
                    maxExpense.subtract(currentExpense)
            );
        });
    }

    public Page<GoalSummaryOutput> toGoalSummaryOutput(Page<Goal> goals, Map<String, BigDecimal> categoryExpenses) {
        return goals.map(g -> {

            BigDecimal maxExpense = g.getMaxExpense();
            BigDecimal currentExpense = categoryExpenses.getOrDefault(g.getCategory().getName(), BigDecimal.ZERO);
            BigDecimal progressPercent = currentExpense.divide(maxExpense, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .min(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.DOWN);

            return new GoalSummaryOutput(
                    g.getCategory().getName(),
                    currentExpense,
                    maxExpense,
                    progressPercent
            );
        });
    }

    public GoalDetailsOutput toGoalDetailsOutput(Goal goal){
        return new GoalDetailsOutput(
                goal.getCategory().getName(),
                goal.getMaxExpense(),
                goal.getStartDate()
        );
    }

    private BigDecimal calculateProgress(BigDecimal current, BigDecimal max) {
        if (max.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.valueOf(100);

        return current.divide(max, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .min(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.DOWN);
    }
}

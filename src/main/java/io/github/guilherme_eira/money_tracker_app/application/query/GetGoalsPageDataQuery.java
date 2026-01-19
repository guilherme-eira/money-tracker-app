package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.goal.GetGoalsPageDataInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.goal.GoalsPageData;
import io.github.guilherme_eira.money_tracker_app.application.gateway.GoalRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.application.mapper.GoalOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GetGoalsPageDataQuery {

    private final GoalRepository goalRepository;
    private final TransactionRepository transactionRepository;
    private final GoalOutputMapper mapper;

    public GoalsPageData execute(GetGoalsPageDataInput query) {
        var pageable = PageRequest.of(query.page(), query.pageSize(), Sort.by("createdAt").descending());
        var startDate = LocalDate.of(query.year(), query.month(), 1);
        var endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        var goals = goalRepository.findGoalsByUserIdAndStartDateBetween(query.userId(), startDate, endDate, pageable);
        var expensesByCategory = transactionRepository.getTotalExpensesByCategory(query.userId(), startDate, endDate);

        return new GoalsPageData(
                mapper.toGoalCardOutput(goals, expensesByCategory),
                startDate
        );
    }


}
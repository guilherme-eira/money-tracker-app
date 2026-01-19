package io.github.guilherme_eira.money_tracker_app.application.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guilherme_eira.money_tracker_app.application.dto.overview.CategoryChartData;
import io.github.guilherme_eira.money_tracker_app.application.dto.overview.DayOfWeekChartData;
import io.github.guilherme_eira.money_tracker_app.application.dto.overview.TransactionSummaryOutput;
import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.TransactionDetailsOutput;
import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.TransactionRowOutput;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TransactionOutputMapper {

    private final ObjectMapper objectMapper;

    public Page<TransactionRowOutput> toTransactionRowOutput(Page<Transaction> transactions) {
        return transactions.map(t -> new TransactionRowOutput(
                t.getId(),
                t.getDate(),
                t.getDescription(),
                t.getCategory().getName(),
                t.getTransactionType(),
                t.getValue()
        ));
    }

    public Page<TransactionSummaryOutput> toTransactionSummaryOutput(Page<Transaction> transactions) {
        return transactions.map(t -> new TransactionSummaryOutput(
                t.getDescription(),
                t.getValue(),
                t.getTransactionType(),
                t.getDate()
        ));
    }

    public TransactionDetailsOutput toTransactionDetailsOutput(Transaction transaction){
        return new TransactionDetailsOutput(
                transaction.getDescription(),
                transaction.getCategory().getId(),
                transaction.getValue(),
                transaction.getDate()
        );
    }

    public String toExpensesByCategoryJson(Map<String, BigDecimal> expensesByCategory) {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();

        expensesByCategory.forEach((categoria, valor) -> {
            labels.add(categoria);
            values.add(valor);
        });

        try {
            return objectMapper.writeValueAsString(new CategoryChartData(labels, values));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toExpensesByDayOfWeekJson(Map<Integer, BigDecimal> expensesByDay) {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();

        Map<Integer, String> daysOfWeek = Map.of(
                1, "Dom", 2, "Seg", 3, "Ter", 4, "Qua", 5, "Qui", 6, "Sex", 7, "Sáb"
        );

        for (int day = 1; day <= 7; day++) {
            labels.add(daysOfWeek.get(day));
            BigDecimal value = expensesByDay.getOrDefault(day, BigDecimal.ZERO);
            values.add(value);
        }

        try {
            return objectMapper.writeValueAsString(new DayOfWeekChartData(labels, values));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

package io.github.guilherme_eira.money_tracker_app.application.dto.transaction;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.CategoryOutput;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TransactionsPageData(
        Page<TransactionRowOutput> transactions,
        BigDecimal filteredIncome,
        BigDecimal filteredExpenses,
        List<CategoryOutput> allCategories,
        String description,
        UUID categoryId,
        LocalDate startDate
) {
}

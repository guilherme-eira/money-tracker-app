package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.TransactionFilters;
import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.GetTransactionsPageDataInput;
import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.TransactionsPageData;
import io.github.guilherme_eira.money_tracker_app.application.gateway.CategoryRepository;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.application.mapper.CategoryOutputMapper;
import io.github.guilherme_eira.money_tracker_app.application.mapper.TransactionOutputMapper;
import io.github.guilherme_eira.money_tracker_app.domain.model.Category;
import io.github.guilherme_eira.money_tracker_app.domain.model.Transaction;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTransactionsPageDataQuery {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionOutputMapper transactionMapper;
    private final CategoryOutputMapper categoryMapper;

    public TransactionsPageData execute(GetTransactionsPageDataInput input) {
        Pageable pageable = PageRequest.of(input.page(), input.pageSize(), Sort.by("date").descending());
        var startDate = LocalDate.of(input.year(), input.month(), 1);
        var endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        var baseFilter = new TransactionFilters(
                input.userId(),
                input.categoryId(),
                null,
                input.description(),
                startDate,
                endDate
        );

        Page<Transaction> transactions = transactionRepository.findTransactionsByFilter(baseFilter, pageable);
        List<Category> categories = categoryRepository.findAllCategories();
        BigDecimal income = transactionRepository.getTotalByFilters(filterWithType(baseFilter, TransactionType.INCOME));
        BigDecimal expenses = transactionRepository.getTotalByFilters(filterWithType(baseFilter, TransactionType.EXPENSE));

        return new TransactionsPageData(
                transactionMapper.toTransactionRowOutput(transactions),
                income,
                expenses,
                categoryMapper.toCategoryOutput(categories),
                input.description(),
                input.categoryId(),
                startDate
        );

    }

    private TransactionFilters filterWithType(TransactionFilters base, TransactionType type){
        return new TransactionFilters(
                base.userId(),
                base.categoryId(),
                type,
                base.description(),
                base.startDate(),
                base.endDate()
        );
    }

}

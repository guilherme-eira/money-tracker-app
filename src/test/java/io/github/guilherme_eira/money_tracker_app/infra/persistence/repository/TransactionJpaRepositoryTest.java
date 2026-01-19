package io.github.guilherme_eira.money_tracker_app.infra.persistence.repository;

import io.github.guilherme_eira.money_tracker_app.application.dto.common.TransactionFilters;
import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.CategoryEntity;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.TransactionEntity;
import io.github.guilherme_eira.money_tracker_app.infra.persistence.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
class TransactionJpaRepositoryTest {

    @Autowired
    private TransactionJpaRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    void shouldReturnIncomeTransactionsForJanuaryOnly() {
        var user = createUser();
        var incomeCategory = createCategory("receita", TransactionType.INCOME);
        var expenseCategory = createCategory("despesa", TransactionType.EXPENSE);

        createTransaction(user, incomeCategory, BigDecimal.valueOf(50), LocalDate.of(2026, 1, 20));

        createTransaction(user, incomeCategory, BigDecimal.valueOf(100), LocalDate.of(2026, 2, 1));
        createTransaction(user, incomeCategory, BigDecimal.valueOf(40), LocalDate.of(2026, 2, 1));
        createTransaction(user, expenseCategory, BigDecimal.valueOf(200), LocalDate.of(2026, 1, 15));

        var filter = new TransactionFilters(
                user.getId(),
                null,
                TransactionType.INCOME,
                null,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );
        var pageable = PageRequest.of(0, 10);



        Page<TransactionEntity> result = repository.findByFilters(filter, pageable);



        Assertions.assertFalse(result.isEmpty(), "O resultado não deveria estar vazio");
        Assertions.assertEquals(1, result.getTotalElements());
        TransactionEntity transaction = result.getContent().getFirst();
        Assertions.assertEquals(0, BigDecimal.valueOf(50).compareTo(transaction.getValue()));
        Assertions.assertEquals(LocalDate.of(2026, 1, 20), transaction.getDate());
        Assertions.assertEquals(TransactionType.INCOME, transaction.getTransactionType());
    }

    @Test
    void shouldReturnEmptyPageWhenNoData() {
        var user = createUser();
        var filter = new TransactionFilters(
                user.getId(),
                null,
                TransactionType.INCOME,
                null,
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 31)
        );
        var pageable = PageRequest.of(0, 10);



        Page<TransactionEntity> result = repository.findByFilters(filter, pageable);



        Assertions.assertTrue(result.isEmpty(), "O resultado deveria estar vazio");
        Assertions.assertEquals(0, result.getTotalElements());
    }

    @Test
    void shouldSumIncomesForJanuaryOnly() {
        var user = createUser();
        var incomeCategory = createCategory("receita", TransactionType.INCOME);
        var expenseCategory = createCategory("despesa", TransactionType.EXPENSE);

        createTransaction(user, incomeCategory, BigDecimal.valueOf(100), LocalDate.of(2026, 1, 10));
        createTransaction(user, incomeCategory, BigDecimal.valueOf(50), LocalDate.of(2026, 1, 20));

        createTransaction(user, expenseCategory, BigDecimal.valueOf(200), LocalDate.of(2026, 1, 15));
        createTransaction(user, incomeCategory, BigDecimal.valueOf(500), LocalDate.of(2026, 2, 1));

        var filter = new TransactionFilters(
                user.getId(),
                null,
                TransactionType.INCOME,
                null,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );



        BigDecimal result = repository.sumByFilters(filter);



        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, BigDecimal.valueOf(150).compareTo(result));
    }

    @Test
    void shouldReturnZeroWhenNoData() {
        var user = createUser();
        var filter = new TransactionFilters(
                user.getId(), null, TransactionType.INCOME, null, LocalDate.now(), LocalDate.now()
        );



        BigDecimal result = repository.sumByFilters(filter);



        Assertions.assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void shouldSumExpensesGroupedByCategory() {
        var user = createUser();

        var catAlimentacao = createCategory("Alimentação", TransactionType.EXPENSE);
        var catTransporte = createCategory("Transporte", TransactionType.EXPENSE);
        createTransaction(user, catAlimentacao, new BigDecimal("100"), LocalDate.of(2026, 1, 10));
        createTransaction(user, catAlimentacao, new BigDecimal("50"), LocalDate.of(2026, 1, 15));
        createTransaction(user, catTransporte, new BigDecimal("30"), LocalDate.of(2026, 1, 10));

        var catSalario = createCategory( "Salário", TransactionType.INCOME);
        createTransaction(user, catSalario, new BigDecimal("5000"), LocalDate.of(2026, 1, 10));
        createTransaction(user, catAlimentacao, new BigDecimal("200"), LocalDate.of(2026, 2, 1));



        var result = repository.sumExpensesGroupedByCategory(
                user.getId(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );



        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
        var item1 = result.getFirst();
        Assertions.assertEquals("Alimentação", item1.categoryName());
        Assertions.assertEquals(0, new BigDecimal("150").compareTo(item1.expenses()));
        var item2 = result.get(1);
        Assertions.assertEquals("Transporte", item2.categoryName());
        Assertions.assertEquals(0, new BigDecimal("30").compareTo(item2.expenses()));
    }

    @Test
    void shouldSumExpensesGroupedByDayOfWeek() {
        var user = createUser();
        var category = createCategory("Geral", TransactionType.EXPENSE);

        createTransaction(user, category, new BigDecimal("100"), LocalDate.of(2026, 1, 11));
        createTransaction(user, category, new BigDecimal("50"), LocalDate.of(2026, 1, 12));
        createTransaction(user, category, new BigDecimal("20"), LocalDate.of(2026, 1, 12));

        var catReceita = createCategory( "Extra", TransactionType.INCOME);
        createTransaction(user, catReceita, new BigDecimal("500"), LocalDate.of(2026, 1, 11));



        var result = repository.sumExpensesGroupedByDayOfWeek(
                user.getId(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 31)
        );



        Assertions.assertEquals(2, result.size());
        var domingo = result.getFirst();
        Assertions.assertEquals(1, domingo.dayOfWeek()); // 1 = Domingo
        Assertions.assertEquals(0, new BigDecimal("100").compareTo(domingo.expenses()));
        var segunda = result.get(1);
        Assertions.assertEquals(2, segunda.dayOfWeek()); // 2 = Segunda
        Assertions.assertEquals(0, new BigDecimal("70").compareTo(segunda.expenses()));
    }



    private void createTransaction(UserEntity user, CategoryEntity category, BigDecimal value, LocalDate date) {
        var transaction = new TransactionEntity(
                UUID.randomUUID(),
                "description",
                value,
                date,
                category.getTransactionType(),
                LocalDateTime.now(),
                null,
                user,
                category
        );
        em.persist(transaction);
    }

    private UserEntity createUser() {
        var u = new UserEntity(
                UUID.randomUUID(),
                "teste",
                "teste",
                "teste",
                null,
                null,
                null,
                LocalDateTime.now(),
                null
        );
        em.persist(u);
        return u;
    }

    private CategoryEntity createCategory(String name, TransactionType type) {
        var c = new CategoryEntity(
                UUID.randomUUID(),
                name,
                type
        );
        em.persist(c);
        return c;
    }
}
package io.github.guilherme_eira.money_tracker_app.application.query;

import io.github.guilherme_eira.money_tracker_app.application.dto.transaction.TransactionDetailsOutput;
import io.github.guilherme_eira.money_tracker_app.application.exception.TransactionNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import io.github.guilherme_eira.money_tracker_app.application.mapper.TransactionOutputMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindTransactionForUpdateQuery {

    private final TransactionRepository repository;
    private final TransactionOutputMapper mapper;

    public TransactionDetailsOutput execute(UUID id) {
        var transaction = repository.findById(id).orElseThrow(TransactionNotFoundException::new);
        return mapper.toTransactionDetailsOutput(transaction);
    }
}

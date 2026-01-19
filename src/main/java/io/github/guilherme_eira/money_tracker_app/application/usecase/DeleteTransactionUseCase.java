package io.github.guilherme_eira.money_tracker_app.application.usecase;

import io.github.guilherme_eira.money_tracker_app.application.exception.TransactionNotFoundException;
import io.github.guilherme_eira.money_tracker_app.application.gateway.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteTransactionUseCase {

    private final TransactionRepository repository;

    @Transactional
    public void execute(UUID id){
        var transaction = repository.findById(id).orElseThrow(TransactionNotFoundException::new);
        repository.delete(transaction);
    }
}

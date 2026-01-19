package io.github.guilherme_eira.money_tracker_app.domain.model;

import io.github.guilherme_eira.money_tracker_app.domain.enumeration.TransactionType;

import java.util.UUID;

public class Category {
    private UUID id;
    private String name;
    private TransactionType transactionType;

    public Category() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}

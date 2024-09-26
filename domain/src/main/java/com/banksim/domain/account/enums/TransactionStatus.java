package com.banksim.domain.account.enums;

public enum TransactionStatus {

    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    CANCELLED("Cancelled"),
    PROCESSING("Processing"),
    REVERSED("Reversed");

    private final String name;

    private TransactionStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

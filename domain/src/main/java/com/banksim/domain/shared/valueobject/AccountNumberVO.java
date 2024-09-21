package com.banksim.domain.shared.valueobject;

import java.util.UUID;

public class AccountNumberVO {

    private final UUID accountNumber;

    public AccountNumberVO() {
        this.accountNumber = UUID.randomUUID();
    }

    public UUID getAccountNumber() {
        return accountNumber;
    }

}

package com.banksim.domain.shared.valueobject;

import java.util.UUID;

public class AccountNumberVO {

    private final UUID number;

    public AccountNumberVO() {
        this.number = UUID.randomUUID();
    }

    public UUID getNumber() {
        return number;
    }

}

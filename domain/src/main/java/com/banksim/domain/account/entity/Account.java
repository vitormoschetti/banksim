package com.banksim.domain.account.entity;

import com.banksim.domain.account.enums.TransactionType;
import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IAggregateRoot;
import com.banksim.domain.shared.notification.DomainNotificationError;
import com.banksim.domain.shared.valueobject.AccountNumberVO;
import com.banksim.domain.shared.valueobject.AuditTimestamps;
import com.banksim.domain.account.valueobject.BalanceVO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Account extends BaseEntity implements IAggregateRoot {

    private final AccountNumberVO accountNumber;
    private final BalanceVO balance;
    private final List<Transaction> transactions;
    private final AuditTimestamps auditTimestamps;

    public Account() {
        this.accountNumber = new AccountNumberVO();
        this.balance = new BalanceVO();
        this.transactions = new ArrayList<>();
        this.auditTimestamps = new AuditTimestamps();
        this.validate();
    }

    public Transaction deposit(BigDecimal amount) {
        final var transaction = new Transaction(TransactionType.DEPOSIT, amount);
        this.transactions.add(transaction);
        this.auditTimestamps.updateNow();
        this.validate();
        return transaction;
    }

    public Transaction withdrawal(BigDecimal amount) {
        final var transaction = new Transaction(TransactionType.WITHDRAWAL, amount);
        this.transactions.add(transaction);
        this.auditTimestamps.updateNow();
        this.validate();
        return transaction;
    }

    public UUID getAccountNumber() {
        return accountNumber.getNumber();
    }

    public BigDecimal getAmount() {
        return balance.getAmount();
    }
    public Instant getCreatedAt() {
        return auditTimestamps.getCreatedAt();
    }

    public Instant getUpdatedAt() {
        return auditTimestamps.getUpdatedAt();
    }


    @Override
    protected void validate() {
        if (Objects.isNull(this.accountNumber)) {
            this.addMessage(new DomainNotificationError("Account number cannot be null"));
        }
        if (Objects.isNull(this.auditTimestamps)) {
            this.addMessage(new DomainNotificationError("Audit cannot be null"));
        }
        this.balance.getMessages().forEach(this::addMessage);
        this.transactions.stream().filter(BaseEntity::hasErrors).map(BaseEntity::getMessages)
                .forEach(m -> m.forEach(this::addMessage));

    }
}

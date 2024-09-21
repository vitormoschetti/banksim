package com.banksim.domain.account.entity;

import com.banksim.domain.account.enums.TransactionType;
import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IAggregateRoot;
import com.banksim.domain.shared.notification.DomainNotificationError;
import com.banksim.domain.shared.valueobject.AccountNumberVO;
import com.banksim.domain.shared.valueobject.AuditTimestamps;
import com.banksim.domain.account.valueobject.BalanceVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void deposit(BigDecimal amount) {
        this.transactions.add(new Transaction(TransactionType.DEPOSIT, amount));
        this.auditTimestamps.updateNow();
    }

    public void withdrawal(BigDecimal amount) {
        this.transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount));
        this.auditTimestamps.updateNow();
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

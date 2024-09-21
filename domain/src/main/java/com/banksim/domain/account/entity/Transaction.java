package com.banksim.domain.account.entity;

import com.banksim.domain.account.enums.TransactionStatus;
import com.banksim.domain.account.enums.TransactionType;
import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IAggregate;
import com.banksim.domain.shared.notification.DomainNotificationError;
import com.banksim.domain.account.valueobject.TransactionValueVO;
import com.banksim.domain.shared.valueobject.AuditTimestamps;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Transaction extends BaseEntity implements IAggregate {

    private final UUID transactionId;
    private final TransactionType type;
    private final TransactionValueVO value;
    private TransactionStatus status;
    private final AuditTimestamps auditTimestamps;

    public Transaction(TransactionType type, BigDecimal amount) {
        this.transactionId = UUID.randomUUID();
        this.type = type;
        this.value = new TransactionValueVO(amount);
        this.status = TransactionStatus.PENDING;
        this.auditTimestamps = new AuditTimestamps();
        this.validate();
    }

    public void toComplete() {
        this.status = TransactionStatus.COMPLETED;
        this.auditTimestamps.updateNow();
    }

    public void toFailed() {
        this.status = TransactionStatus.FAILED;
        this.auditTimestamps.updateNow();
    }

    public void toCancelled() {
        this.status = TransactionStatus.CANCELLED;
        this.auditTimestamps.updateNow();
    }

    public void toProcessing() {
        this.status = TransactionStatus.PROCESSING;
        this.auditTimestamps.updateNow();
    }

    public void toReversed() {
        this.status = TransactionStatus.REVERSED;
        this.auditTimestamps.updateNow();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value.getAmount();
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Instant getCreateAt() {
        return auditTimestamps.getCreatedAt();
    }

    public Instant getUpdateAt() {
        return auditTimestamps.getUpdatedAt();
    }

    @Override
    protected void validate() {
        if(Objects.isNull(this.transactionId)) {
            this.addMessage(new DomainNotificationError("Transaction id is required"));
        }
        if(Objects.isNull(this.type)) {
            this.addMessage(new DomainNotificationError("Transaction type is required"));
        }
        if(Objects.isNull(this.status)) {
            this.addMessage(new DomainNotificationError("Transaction status is required"));
        }
        if(value.hasErrors()) {
            value.getMessages().forEach(this::addMessage);
        }
        if(Objects.isNull(auditTimestamps)) {
            this.addMessage(new DomainNotificationError("Transaction audit timestamp is required"));
        }
        if(TransactionType.DEPOSIT.equals(this.type) && value.isNegative()) {
            this.addMessage(new DomainNotificationError("Deposit cannot be negative amount"));
        }
        if(TransactionType.WITHDRAWAL.equals(this.type) && value.isPositive()) {
            this.addMessage(new DomainNotificationError("Withdrawal cannot be positive amount"));
        }
    }
}

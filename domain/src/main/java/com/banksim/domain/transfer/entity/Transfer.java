package com.banksim.domain.transfer.entity;

import com.banksim.domain.account.entity.Account;
import com.banksim.domain.transfer.enums.TransferStatus;
import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IAggregateRoot;
import com.banksim.domain.shared.notification.DomainNotificationError;
import com.banksim.domain.shared.valueobject.AuditTimestamps;
import com.banksim.domain.shared.valueobject.TransferValueVO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Transfer extends BaseEntity implements IAggregateRoot {

    private final UUID transferId;
    private final Account fromAccount;
    private final Account toAccount;
    private final TransferValueVO amount;
    private TransferStatus status;
    private final AuditTimestamps auditTimestamps;

    public Transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        this.transferId = UUID.randomUUID();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = new TransferValueVO(amount);
        this.status = TransferStatus.PENDING;
        this.auditTimestamps = new AuditTimestamps();
        this.validate();
    }

    public void toComplete() {
        this.status = TransferStatus.COMPLETED;
        this.auditTimestamps.updateNow();
        this.validate();
    }

    public void toFailed() {
        this.status = TransferStatus.FAILED;
        this.auditTimestamps.updateNow();
        this.validate();
    }

    public UUID getTransferId() {
        return transferId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount.getAmount();
    }

    public TransferStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return auditTimestamps.getCreatedAt();
    }

    public Instant getUpdatedAt() {
        return auditTimestamps.getCreatedAt();
    }

    @Override
    protected void validate() {
        if (Objects.isNull(this.transferId))
            this.addMessage(new DomainNotificationError("Transfer id is required"));
        if (Objects.isNull(this.fromAccount))
            this.addMessage(new DomainNotificationError("From account is required"));
        if (Objects.isNull(this.toAccount))
            this.addMessage(new DomainNotificationError("To account is required"));
        if (Objects.isNull(this.status))
            this.addMessage(new DomainNotificationError("Status is required"));
        this.amount.getMessages().forEach(this::addMessage);
    }
}

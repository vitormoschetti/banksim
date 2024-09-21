package com.banksim.domain.shared.valueobject;

import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IValueObject;
import com.banksim.domain.shared.notification.DomainNotificationError;

import java.math.BigDecimal;

public class TransactionValueVO extends BaseEntity implements IValueObject {

    private final BigDecimal amount;

    public TransactionValueVO(BigDecimal amount) {
        this.amount = amount;
        this.validate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    @Override
    protected void validate() {
        if (amount.compareTo(BigDecimal.ZERO) == 0)
            this.addMessage(new DomainNotificationError("Balance cannot be zero"));
    }
}

package com.banksim.domain.shared.valueobject;

import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IValueObject;
import com.banksim.domain.shared.notification.DomainNotificationError;

import java.math.BigDecimal;

public class TransferValueVO extends BaseEntity implements IValueObject {

    private final BigDecimal amount;

    public TransferValueVO(BigDecimal amount) {
        this.amount = amount;
        this.validate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    protected void validate() {
        if (amount.compareTo(BigDecimal.ZERO) > 0)
            this.addMessage(new DomainNotificationError("Balance cannot be zero or negative"));
    }
}

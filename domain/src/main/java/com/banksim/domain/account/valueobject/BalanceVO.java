package com.banksim.domain.account.valueobject;

import com.banksim.domain.shared.entity.BaseEntity;
import com.banksim.domain.shared.entity.IValueObject;
import com.banksim.domain.shared.notification.DomainNotificationError;

import java.math.BigDecimal;

public class BalanceVO extends BaseEntity implements IValueObject {

    private BigDecimal amount;

    public BalanceVO() {
        this.amount = BigDecimal.ZERO;
    }

    public void add(BigDecimal add) {
        if (add.compareTo(BigDecimal.ZERO) < 1) {
            this.addMessage(new DomainNotificationError("Amount can not be less than zero"));
        }
        this.amount = this.amount.add(add);
    }

    public void subtract(BigDecimal subtract) {
        if (subtract.compareTo(BigDecimal.ZERO) < 1) {
            this.addMessage(new DomainNotificationError("Amount can not be less than zero"));
        }
        this.amount = this.amount.subtract(subtract);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    protected void validate() {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            this.addMessage(new DomainNotificationError("Balance cannot be negative"));
    }
}

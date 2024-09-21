package com.banksim.domain.shared.entity;

import com.banksim.domain.shared.notification.DomainNotification;
import com.banksim.domain.shared.notification.INotification;
import com.banksim.domain.shared.notification.INotificationError;

import java.util.Set;

public abstract class BaseEntity {

    private final INotification notification;

    protected BaseEntity() {
        this.notification = new DomainNotification();
    }

    public Boolean hasErrors() {
        return !this.notification.messages().isEmpty();
    }

    public Set<INotificationError> getMessages() {
        return this.notification.messages();
    }

    public void addMessage(final INotificationError error) {
        this.notification.addMessage(error);
    }

    protected abstract void validate();

}

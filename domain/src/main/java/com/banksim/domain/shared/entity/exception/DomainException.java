package com.banksim.domain.shared.entity.exception;

import com.banksim.domain.shared.notification.INotificationError;

import java.util.Set;
import java.util.stream.Collectors;

public class DomainException extends RuntimeException {

    public DomainException(final Set<INotificationError> messages) {
        super(messages.stream().map(INotificationError::message).collect(Collectors.joining(", ")));
    }
}

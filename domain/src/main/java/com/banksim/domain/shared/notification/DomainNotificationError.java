package com.banksim.domain.shared.notification;

import java.util.Objects;

public record DomainNotificationError(String message) implements INotificationError {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final DomainNotificationError that)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}

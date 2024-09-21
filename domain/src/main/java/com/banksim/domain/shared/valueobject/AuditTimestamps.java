package com.banksim.domain.shared.valueobject;

import java.time.Instant;
import java.time.ZoneOffset;

public class AuditTimestamps {

    private Instant createdAt;
    private Instant updatedAt;

    public AuditTimestamps() {
        createNow();
    }

    public AuditTimestamps(Instant createdAt, Instant updatedAt) {
        this.createdAt = createdAt.atOffset(ZoneOffset.UTC).toInstant();
        this.updatedAt = updatedAt.atOffset(ZoneOffset.UTC).toInstant();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void createNow() {
        this.createdAt = Instant.now().atOffset(ZoneOffset.UTC).toInstant();
    }

    public void updateNow() {
        this.updatedAt = Instant.now().atOffset(ZoneOffset.UTC).toInstant();
    }

}

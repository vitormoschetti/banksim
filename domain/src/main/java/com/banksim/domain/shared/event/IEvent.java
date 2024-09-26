package com.banksim.domain.shared.event;

import java.time.Instant;
import java.util.UUID;

public interface IEvent<T extends IRecord> {

    String getEventName();

    //Testing default methods
    default UUID getTraceId() {
        return UUID.randomUUID();
    }

    default Instant getInstantCreated() {
        return Instant.now();
    }

    T getPayload();


}

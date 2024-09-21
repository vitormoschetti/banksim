package com.banksim.domain.shared.event;

import java.time.Instant;
import java.util.UUID;

public interface IEvent<T extends IRecord> {

    String getEventName();

    UUID getTraceId();

    Instant getInstantCreated();

    T getPayload();


}

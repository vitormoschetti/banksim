package com.banksim.domain.account.notification.event;

import com.banksim.domain.shared.event.IEvent;

public class ProcessingTransactionEvent implements IEvent<ProcessingTransactionRecord> {

    private final ProcessingTransactionRecord record;

    public ProcessingTransactionEvent(ProcessingTransactionRecord record) {
        this.record = record;
    }

    @Override
    public String getEventName() {
        return "ProcessingTransaction".concat(record.status().name());
    }

    @Override
    public ProcessingTransactionRecord getPayload() {
        return record;
    }
}

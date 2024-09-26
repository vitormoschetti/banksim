package com.banksim.domain.account.notification.event;

import com.banksim.domain.account.enums.TransactionStatus;
import com.banksim.domain.shared.event.IRecord;

import java.util.UUID;

public record ProcessingTransactionRecord(UUID transactionId, TransactionStatus status) implements IRecord {
}

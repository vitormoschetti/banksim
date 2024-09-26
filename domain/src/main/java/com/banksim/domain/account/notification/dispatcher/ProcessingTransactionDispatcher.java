package com.banksim.domain.account.notification.dispatcher;

import com.banksim.domain.account.notification.event.ProcessingTransactionEvent;
import com.banksim.domain.shared.event.IDispatcher;

public interface ProcessingTransactionDispatcher extends IDispatcher<ProcessingTransactionEvent> {

}

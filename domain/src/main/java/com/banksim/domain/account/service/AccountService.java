package com.banksim.domain.account.service;

import com.banksim.domain.account.entity.Account;
import com.banksim.domain.account.entity.Transaction;
import com.banksim.domain.account.notification.dispatcher.ProcessingTransactionDispatcher;
import com.banksim.domain.account.notification.event.ProcessingTransactionEvent;
import com.banksim.domain.account.notification.event.ProcessingTransactionRecord;
import com.banksim.domain.account.repository.AccountRepository;
import com.banksim.domain.shared.entity.exception.DomainException;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {

    private final AccountRepository accountRepository;
    private final ProcessingTransactionDispatcher dispatcher;

    public AccountService(AccountRepository accountRepository, ProcessingTransactionDispatcher dispatcher) {
        this.accountRepository = accountRepository;
        this.dispatcher = dispatcher;
    }

    public UUID createAccount() {

        final var account = new Account();

        validateAccount(account);

        accountRepository.create(account);

        return account.getAccountNumber();
    }

    public Account findByAccountNumber(UUID accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    public Transaction deposit(UUID accountNumber, BigDecimal amount) {
        final var account = findByAccountNumber(accountNumber);
        final var transaction = account.deposit(amount);

        validateAccount(account);

        accountRepository.update(account);

        dispatcher.dispatch(new ProcessingTransactionEvent(new ProcessingTransactionRecord(transaction.getTransactionId(), transaction.getStatus())));

        return transaction;

    }

    public Transaction withdrawal(UUID accountNumber, BigDecimal amount) {
        final var account = findByAccountNumber(accountNumber);
        final var transaction = account.withdrawal(amount);

        validateAccount(account);

        accountRepository.update(account);

        dispatcher.dispatch(new ProcessingTransactionEvent(new ProcessingTransactionRecord(transaction.getTransactionId(), transaction.getStatus())));

        return transaction;
    }

    private void validateAccount(Account account) {
        if (account.hasErrors())
            throw new DomainException(account.getMessages());
    }

}

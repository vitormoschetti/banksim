package com.banksim.domain.account.service;

import com.banksim.domain.account.entity.Account;
import com.banksim.domain.account.repository.AccountRepository;
import com.banksim.domain.shared.entity.exception.DomainException;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UUID createAccount() {

        final var account = new Account();

        accountRepository.create(account);

        return account.getAccountNumber();
    }

    public Account findByAccountNumber(UUID accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    public void deposit(UUID accountNumber, BigDecimal amount) {
        final var account = findByAccountNumber(accountNumber);
        final var transaction = account.deposit(amount);

        validateAccount(account);

        accountRepository.update(account);

    }


    public void withdrawal(UUID accountNumber, BigDecimal amount) {
        final var account = findByAccountNumber(accountNumber);
        final var transaction = account.withdrawal(amount);

        validateAccount(account);

        accountRepository.update(account);
    }

    private void validateAccount(Account account) {
        if (account.hasErrors())
            throw new DomainException(account.getMessages());
    }

}

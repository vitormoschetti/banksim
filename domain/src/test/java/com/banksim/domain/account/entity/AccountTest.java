package com.banksim.domain.account.entity;

import com.banksim.domain.account.enums.TransactionType;
import com.banksim.domain.shared.notification.DomainNotificationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    @DisplayName("should open new account")
    void shouldOpenNewAccount() {
        final var account = new Account();

        assertNotNull(account);
        assertNotNull(account.getAccountNumber());
        assertNotNull(account.getAmount());
        assertEquals(BigDecimal.ZERO, account.getAmount());
        assertNotNull(account.getCreatedAt());
        assertNull(account.getUpdatedAt());

    }

    @Test
    @DisplayName("should notify deposit with negative value")
    void shouldNotifyDepositWithNegativeValue() {

        //scenario
        final var account = new Account();

        //execution
        account.deposit(BigDecimal.valueOf(-100));

        //validation
        assertTrue(account.hasErrors());
        assertEquals(1, account.getMessages().size());
        assertEquals(new DomainNotificationError("Deposit cannot be negative amount"), account.getMessages().stream().findFirst().orElse(null));

    }

    @Test
    @DisplayName("should open new transaction to deposit")
    void shouldOpenNewTransactionToDeposit() {

        //scenario
        final var account = new Account();

        //execution
        final var transaction = account.deposit(BigDecimal.valueOf(100));

        //validation
        assertFalse(account.hasErrors());
        assertNotNull(account.getUpdatedAt());
        assertNotNull(transaction);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(BigDecimal.valueOf(100), transaction.getValue());

    }

    @Test
    @DisplayName("should open new transaction to withdrawal")
    void shouldOpenNewTransactionToWithdrawal() {

        //scenario
        final var account = new Account();

        //execution
        final var transaction = account.withdrawal(BigDecimal.valueOf(-100));

        //validation
        assertFalse(account.hasErrors());
        assertNotNull(account.getUpdatedAt());
        assertNotNull(transaction);
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
        assertEquals(BigDecimal.valueOf(-100), transaction.getValue());

    }

    @Test
    @DisplayName("should notify withdrawal with positive value")
    void shouldNotifyDepositWithPositiveValue() {

        //scenario
        final var account = new Account();

        //execution
        account.withdrawal(BigDecimal.valueOf(100));

        //validation
        assertTrue(account.hasErrors());
        assertEquals(1, account.getMessages().size());
        assertEquals(new DomainNotificationError("Withdrawal cannot be positive amount"), account.getMessages().stream().findFirst().orElse(null));

    }


}

package com.banksim.domain.account.entity;

import com.banksim.domain.account.enums.TransactionStatus;
import com.banksim.domain.account.enums.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    @DisplayName("Validate values new transaction")
    void validateNewTransaction() {

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, BigDecimal.ONE);

        assertFalse(transaction.hasErrors());
        assertNotNull(transaction.getTransactionId());
        assertEquals(TransactionType.DEPOSIT,transaction.getType());
        assertEquals(BigDecimal.ONE, transaction.getValue());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        assertNotNull(transaction.getCreateAt());

    }

    @Test
    @DisplayName("should notify an error when deposit be negative amount")
    void shouldNotifyErrorWhenDepositNegativeAmount() {

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, BigDecimal.ONE.negate());

        assertTrue(transaction.hasErrors());
        assertEquals(1, transaction.getMessages().size());

    }

    @Test
    @DisplayName("should notify an error when withdrawal be positive amount")
    void shouldNotifyErrorWhenWithdrawalPositiveAmount() {

        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, BigDecimal.ONE);

        assertTrue(transaction.hasErrors());
        assertEquals(1, transaction.getMessages().size());

    }


}
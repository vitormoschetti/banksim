package com.banksim.domain.account.service;

import com.banksim.domain.account.entity.Account;
import com.banksim.domain.account.entity.Transaction;
import com.banksim.domain.account.enums.TransactionStatus;
import com.banksim.domain.account.notification.dispatcher.ProcessingTransactionDispatcher;
import com.banksim.domain.account.notification.event.ProcessingTransactionEvent;
import com.banksim.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ProcessingTransactionDispatcher dispatcher;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @Captor
    ArgumentCaptor<ProcessingTransactionEvent> eventCaptor;

    @Test
    @DisplayName("should create new account")
    void shouldCreateNewAccount() {

        //scenario
        doNothing().when(accountRepository).create(any(Account.class));

        //execution
        final var accountNumber = this.accountService.createAccount();

        //validation
        verify(accountRepository, times(1)).create(any(Account.class));
        verify(accountRepository).create(accountCaptor.capture());

        assertNotNull(accountNumber);

        final var accountSave = accountCaptor.getValue();

        assertNotNull(accountSave);
        assertEquals(accountNumber, accountSave.getAccountNumber());

    }

    @Test
    @DisplayName("should open a new deposit transaction")
    void shouldOpenNewDepositTransaction() {

        //scenario
        final var newAccount = this.createAccount();

        when(accountRepository.findById(newAccount.getAccountNumber())).thenReturn(newAccount);
        doNothing().when(accountRepository).update(newAccount);
        doNothing().when(dispatcher).dispatch(any(ProcessingTransactionEvent.class));

        //execution
        final var transaction = accountService.deposit(newAccount.getAccountNumber(), BigDecimal.TEN);

        //validation
        verify(accountRepository).update(accountCaptor.capture());
        verify(dispatcher).dispatch(eventCaptor.capture());

        assertNotNull(transaction);
        final var event = eventCaptor.getValue();
        final var account = accountCaptor.getValue();

        assertNotNull(event);
        assertNotNull(account);

        assertEquals(newAccount.getAccountNumber(), account.getAccountNumber());
        assertEquals(TransactionStatus.PENDING, event.getPayload().status());
        assertEquals(transaction.getTransactionId(), event.getPayload().transactionId());

    }


    @Test
    @DisplayName("should open a new withdrawal transaction")
    void shouldOpenNewWithdrawalTransaction() {

        //scenario
        final var newAccount = this.createAccount();

        when(accountRepository.findById(newAccount.getAccountNumber())).thenReturn(newAccount);
        doNothing().when(accountRepository).update(newAccount);
        doNothing().when(dispatcher).dispatch(any(ProcessingTransactionEvent.class));

        //execution
        final var transaction = accountService.withdrawal(newAccount.getAccountNumber(), BigDecimal.valueOf(-10));

        //validation
        verify(accountRepository).update(accountCaptor.capture());
        verify(dispatcher).dispatch(eventCaptor.capture());

        assertNotNull(transaction);
        final var event = eventCaptor.getValue();
        final var account = accountCaptor.getValue();

        assertNotNull(event);
        assertNotNull(account);

        assertEquals(newAccount.getAccountNumber(), account.getAccountNumber());
        assertEquals(TransactionStatus.PENDING, event.getPayload().status());
        assertEquals(transaction.getTransactionId(), event.getPayload().transactionId());

    }


    private Account createAccount() {
        return new Account();
    }


}
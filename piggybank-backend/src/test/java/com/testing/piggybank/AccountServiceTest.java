package com.testing.piggybank;

import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    public void getAccount_AccountsPresent_returnAccounts() {
        // Arrange
        Optional<Account> optionalAccount = Optional.of(new Account());
        Mockito.when(accountRepository.findById(1L)).thenReturn(optionalAccount);

        // Act
        Optional<Account> result = accountService.getAccount(1L);

        // Assert
        Assertions.assertEquals(result, optionalAccount);
        Mockito.verify(accountRepository).findById(1L);
    }
    @Test
    public void GetAccountsByUserId_UserHasAccounts_ReturnAccountslist() {
        // Arrange
        long userId = 123;
        List<Account> userAccounts = Arrays.asList(new Account(), new Account());
        Mockito.when(accountRepository.findAllByUserId(userId)).thenReturn(userAccounts);

        // Act
        List<Account> result = accountService.getAccountsByUserId(userId);

        // Assert
        Assertions.assertEquals(result, userAccounts);
        Mockito.verify(accountRepository).findAllByUserId(userId);
    }
    @Test
    public void updateBalance_CreditDirection_UpdateBalanceCorrectly() {
        // Arrange
        long accountId = 1L;
        BigDecimal amount = new BigDecimal("50.00");
        Account account = new Account();
        account.setBalance(new BigDecimal("100.00"));

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        accountService.updateBalance(accountId, amount, Direction.CREDIT);

        // Assert
        Assertions.assertEquals(new BigDecimal("50.00"), account.getBalance());
        Mockito.verify(accountRepository).save(account);
    }

}
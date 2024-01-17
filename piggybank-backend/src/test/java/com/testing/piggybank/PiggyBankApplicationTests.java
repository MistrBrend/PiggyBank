package com.testing.piggybank;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.account.AccountService;
import com.testing.piggybank.account.UpdateAccountRequest;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Direction;

import java.math.BigDecimal;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PiggyBankApplicationTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testingTest(){
        int number = 1;
        assertEquals(number, 1, "they are not equal");
    }

    @Test
    void testAccountResponseGettersAndSetters() {
        AccountResponse accountResponse = new AccountResponse();
        BigDecimal balance = new BigDecimal("100.50");
        String name = "John Doe";
        long id = 123;

        // When
        accountResponse.setBalance(balance);
        accountResponse.setName(name);
        accountResponse.setId(id);

        // Then
        assertEquals(balance, accountResponse.getBalance(), "Balance should match");
        assertEquals(name, accountResponse.getName(), "Name should match");
        assertEquals(id, accountResponse.getId(), "ID should match");
    }

    @Test
    void testGetAccount() {
        long accountId = 1;
        Account expectedAccount = new Account();
        expectedAccount.setId(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(expectedAccount));

        // When
        Optional<Account> result = accountService.getAccount(accountId);

        // Then
        assertEquals(expectedAccount, result.orElse(null), "Account should match");
    }

    @Test
    void testUpdateBalance() {
        // Given
        long accountId = 1;
        BigDecimal initialBalance = new BigDecimal("100.00");
        Account accountToUpdate = new Account();
        accountToUpdate.setId(accountId);
        accountToUpdate.setBalance(initialBalance);

        BigDecimal amount = new BigDecimal("20.50");
        Direction direction = Direction.CREDIT;

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountToUpdate));
        when(accountRepository.save(Mockito.any())).thenReturn(accountToUpdate);

        // When
        accountService.updateBalance(accountId, amount, direction);

        // Then
        assertEquals(initialBalance.subtract(amount), accountToUpdate.getBalance(), "Balance should be updated");
    }

    @Test
    void testValidUpdateAccountRequest() {
        // Given
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setAccountId(1L);
        request.setAccountName("Savings");

        // When
        Set<ConstraintViolation<UpdateAccountRequest>> violations = validator.validate(request);

        // Then
        assertEquals(0, violations.size(), "There should be no validation violations");
    }

    @Test
    void testInvalidUpdateAccountRequest() {
        // Given
		UpdateAccountRequest request = new UpdateAccountRequest();
		// accountId is missing, and accountName is empty

		// When
		Set<ConstraintViolation<UpdateAccountRequest>> violations = validator.validate(request);

		// Then
		assertEquals(2, violations.size(), "There should be two validation violations");
    }
}

package com.testing.piggybank;

import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PiggyBankApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testingGetAllTransactions() {

        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", "1");

        // Act
        ResponseEntity<GetTransactionsResponse> response = restTemplate
                .getForEntity("/api/v1/transactions/1", GetTransactionsResponse.class);

        // Assert
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
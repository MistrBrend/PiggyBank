package com.testing.piggybank;

import com.testing.piggybank.account.AccountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAccount() {
        ResponseEntity<AccountResponse> response = restTemplate.getForEntity("/api/v1/accounts/{accountId}", AccountResponse.class, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void testGetAccounts() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", "123");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AccountResponse> response = restTemplate.exchange(
                "/api/v1/accounts",
                HttpMethod.GET,
                entity,
                AccountResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}

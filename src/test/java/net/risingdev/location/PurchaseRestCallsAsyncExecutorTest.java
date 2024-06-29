package net.risingdev.location;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import net.risingdev.location.entities.Purchase;
import net.risingdev.location.service.PurchaseRestCallsAsyncExecutor;

@ExtendWith(MockitoExtension.class)
public class PurchaseRestCallsAsyncExecutorTest {
	
	@Mock
	private RestTemplate restTemplate;
	
	private static final String MOCK_BASE_URL = "https://internal-api.com";
	
	PurchaseRestCallsAsyncExecutor subject;
	
	@BeforeEach
	void setUp() {
		subject = new PurchaseRestCallsAsyncExecutor(restTemplate);
	}
	
	@Test
	void testSuccessfulPurchases() {
		
		Purchase purchase = new Purchase("1", "1", "1");
		
		List<Purchase> purchases = new ArrayList<>(List.of(purchase));
		
		when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/users/1"), String.class)).thenReturn(new ResponseEntity<>("User 1", HttpStatus.OK));
		when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/orders/1"), String.class)).thenReturn(new ResponseEntity<>("Order 1", HttpStatus.OK));
		when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/payments/1"), String.class)).thenReturn(new ResponseEntity<>("Payment 1", HttpStatus.OK));
		
		subject.updatePurchases(purchases);
		
		assertEquals("User 1", purchases.get(0).getBuyerName());
		assertEquals("Order 1", purchases.get(0).getOrderDescription());
		assertEquals("Payment 1", purchases.get(0).getPaymentDescription());
	}
	
	

}

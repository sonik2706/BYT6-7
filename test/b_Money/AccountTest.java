package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(100_000_00, SEK));

		SweBank.deposit("Alice", new Money(10_000_00, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
	    testAccount.addTimedPayment("tp1", 3, 1, new Money(10_000_00, SEK), SweBank, "Alice");
	    testAccount.addTimedPayment("tp2", 3, 1, new Money(10_000_00, SEK), SweBank, "Alice");
	    assertTrue(testAccount.timedPaymentExists("tp1"));
	    assertTrue(testAccount.timedPaymentExists("tp2"));
	    testAccount.removeTimedPayment("tp1");
	    assertFalse(testAccount.timedPaymentExists("tp1"));
	    assertTrue(testAccount.timedPaymentExists("tp2"));
	}
		
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
	    testAccount.addTimedPayment("tp1", 3, 1, new Money(10_000_00, SEK), SweBank, "Alice");
	    testAccount.tick();
	    assertEquals(20_000.00, SweBank.getBalance("Alice"), 0);
	    assertTrue(new Money(90_000_00, SEK).equals(testAccount.getBalance()));
	    testAccount.tick();
	    testAccount.tick();
	    testAccount.tick();
	    assertEquals(30_000.00, SweBank.getBalance("Alice"), 0);
	    assertTrue(new Money(80_000_00, SEK).equals(testAccount.getBalance()));
	}

	@Test
	public void testAddWithdraw() {
	    Money m = new Money(100_000_00, SEK);
	    assertTrue(m.equals(testAccount.getBalance()));
	    testAccount.deposit(m);
	    assertTrue(m.add(m).equals(testAccount.getBalance()));
	    testAccount.withdraw(m.add(m));
	    assertTrue(new Money(0, SEK).equals(testAccount.getBalance()));
	}

	@Test
	public void testGetBalance() {
	    assertTrue(new Money(100_000_00, SEK).equals(testAccount.getBalance()));
	}
}
